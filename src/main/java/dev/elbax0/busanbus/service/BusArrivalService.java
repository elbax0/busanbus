package dev.elbax0.busanbus.service;

import dev.elbax0.busanbus.controller.dto.BusArrivalResponse;
import dev.elbax0.busanbus.service.dto.ApiBusArrivalResponse;
import dev.elbax0.busanbus.util.XmlParseUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class BusArrivalService {

    private final ConcurrentHashMap<Long, AtomicInteger> clientCount = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, BusArrivalResponse> arrivalTable = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(BusArrivalService.class);

    @Value("${BUSAN_BIMS_API_SERVICE_KEY}")
    private String BUSAN_BIMS_API_SERVICE_KEY;

    @Resource
    private final WebClient busanBimsClient;

    private final XmlParseUtil xmlParseUtil;

    public int increment(Long busStopId) {
        if (clientCount.getOrDefault(busStopId, new AtomicInteger(0)).get() < 1) {
            clientCount.put(busStopId, new AtomicInteger(0));
            updateArrivalDataWithBlocking(busStopId);
        }

        return clientCount.get(busStopId).incrementAndGet();
    }

    public int decrement(Long busStopId) {
        return clientCount.get(busStopId).decrementAndGet();
    }

    public BusArrivalResponse get(Long busStopId) {
        return arrivalTable.get(busStopId);
    }


    private void updateArrivalDataWithBlocking(Long busStopId) {
        logger.info("Init Bus Arrival Data - {}", busStopId);

        ApiBusArrivalResponse apiResponse = busanBimsClient.get()
                .uri("stopArrByBstopid?bstopid={bstopid}&serviceKey={BUSAN_BIMS_API_SERVICE_KEY}",
                        busStopId, BUSAN_BIMS_API_SERVICE_KEY)
                .exchangeToMono(response -> {
                    if (response.statusCode() != HttpStatusCode.valueOf(200)) {
                        throw new RuntimeException();
                    }
                    return response.bodyToMono(String.class);
                })
                .map(str -> xmlParseUtil.readValue(str, ApiBusArrivalResponse.class))
                .block();

        arrivalTable.put(busStopId, new BusArrivalResponse(busStopId, apiResponse));
        logger.info("Initial Bus Arrival Data has been set - {}", busStopId);
    }

    public void updateArrivalData() {
        updateArrivalData(clientCount.keySet());
    }

    private void updateArrivalData(Iterable<Long> busStopIds) {
        Flux.fromIterable(busStopIds)
                .filter(busStopId -> clientCount.get(busStopId).get() > 0)
                .subscribe(busStopId -> {
                    logger.info("Update Bus Arrival Data - {}", busStopId);

                    busanBimsClient.get()
                            .uri("stopArrByBstopid?bstopid={bstopid}&serviceKey={serviceKey}",
                                    busStopId, BUSAN_BIMS_API_SERVICE_KEY)
                            .exchangeToMono(response -> {
                                if (response.statusCode() != HttpStatusCode.valueOf(200)) {
                                    throw new RuntimeException();
                                }
                                return response.bodyToMono(String.class);
                            })
                            .map(str -> xmlParseUtil.readValue(str, ApiBusArrivalResponse.class))
                            .subscribe(busArrivalResponse -> {
                                arrivalTable.put(busStopId, new BusArrivalResponse(busStopId, busArrivalResponse));
                                logger.info("Bus Arrival Data is updated - {}", busStopId);
                            });
                });
    }
}
