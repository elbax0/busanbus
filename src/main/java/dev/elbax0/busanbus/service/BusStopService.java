package dev.elbax0.busanbus.service;

import dev.elbax0.busanbus.controller.dto.BusStopResponse;
import dev.elbax0.busanbus.domain.BusStop;
import dev.elbax0.busanbus.domain.BusStopMapImage;
import dev.elbax0.busanbus.repository.BusStopMapImageRepository;
import dev.elbax0.busanbus.repository.BusStopRepository;
import dev.elbax0.busanbus.service.dto.ApiBusStopDto;
import dev.elbax0.busanbus.service.dto.ApiBusStopResponse;
import dev.elbax0.busanbus.util.XmlParseUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusStopService {

    private final BusStopRepository busStopRepository;
    private final BusStopMapImageRepository busStopMapImageRepository;
    private final XmlParseUtil xmlParseUtil;
    private final Logger logger = LoggerFactory.getLogger(BusStopService.class);

    @Resource
    private final WebClient busanBimsClient;

    @Resource
    private final WebClient naverMapClient;

    @Value("${BUSAN_BIMS_API_SERVICE_KEY}")
    private String BUSAN_BIMS_API_SERVICE_KEY;

    @Transactional
    public void init() {
        if (busStopRepository.count() > 0) {
            return;
        }

        logger.info("initialize bus stop list");

        int numOfRows = 9999;
        String xmlData = busanBimsClient.get()
                .uri("busStopList?pageNo=1&numOfRows={numOfRows}&serviceKey={serviceKey}",
                        numOfRows, BUSAN_BIMS_API_SERVICE_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        ApiBusStopResponse apiResult = xmlParseUtil.readValue(xmlData, ApiBusStopResponse.class);
        List<ApiBusStopDto> busStopDtoList = apiResult.getBody().getItems();
        List<BusStop> busStopList = busStopDtoList.stream()
                .map(ApiBusStopDto::toEntity)
                .toList();
        busStopRepository.saveAll(busStopList);
    }


    @Transactional(readOnly = true)
    public BusStopResponse search(String keyword, int page) {
        logger.info("search {}", keyword);
        Pageable pageable = PageRequest.of(page, 10);
        Page<BusStop> result = busStopRepository.findByKeyword(keyword, pageable);
        return new BusStopResponse(keyword, result);
    }

    // 버스 정류장 지도 정보 제공
    @Transactional
    public byte[] getBusStopMapImage(Long busStopId, Integer zoomLevel) {
        logger.info("search map {}-[{}]", busStopId, zoomLevel);
        BusStop busStop = busStopRepository.findByBusStopId(busStopId);
        Optional<BusStopMapImage> busStopMapImage = busStopMapImageRepository.getBusStopMapImage(busStop, zoomLevel);
        if (busStopMapImage.isPresent()) {
            return busStopMapImage.get().getBinaryData();
        }

        String gpsX = busStop.getGpsX();
        String gpsY = busStop.getGpsY();
        byte[] image = naverMapClient.get()
                .uri("?w=400&h=500&center={gpsX},{gpsY}&markers=pos:{gpsX} {gpsY}&level={zoomLevel}&public_transit&format=png",
                        gpsX, gpsY, gpsX, gpsY, zoomLevel)
                .accept(MediaType.IMAGE_PNG)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();

        BusStopMapImage busStopMapImageData = new BusStopMapImage(busStop, zoomLevel, image);
        busStopMapImageRepository.save(busStopMapImageData);

        return image;
    }
}
