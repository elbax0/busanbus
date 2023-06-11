package dev.elbax0.busanbus.controller;

import dev.elbax0.busanbus.controller.dto.BusArrivalResponse;
import dev.elbax0.busanbus.service.BusArrivalService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class BusArrivalController {

    private final BusArrivalService busArrivalService;
    private final Logger logger = LoggerFactory.getLogger(BusArrivalController.class);

    // 버스 도착 정보를 SSE를 이용해서 6초마다 클라이언트에게 전송
    @GetMapping(value = "/api/busStop/{busStopId}/arrival/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BusArrivalResponse> arrivalSse(
            @PathVariable(value = "busStopId") Long busStopId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // Nginx proxy_buffering off
        response.setHeader("X-Accel-Buffering", "no");

        logger.info("Bus Arrival Data - {}++", busStopId);
        busArrivalService.increment(busStopId);

        return Flux.just(-1L)
                .concatWith(Flux.interval(Duration.ofSeconds(6)))
                .map(i -> busArrivalService.get(busStopId))
                .doOnCancel(() -> {
                    logger.info("Bus Arrival Data - {}--", busStopId);
                    busArrivalService.decrement(busStopId);
                });
    }
}
