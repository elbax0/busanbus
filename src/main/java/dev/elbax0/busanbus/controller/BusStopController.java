package dev.elbax0.busanbus.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.elbax0.busanbus.service.BusStopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BusStopController {

    private final BusStopService busStopService;

    // 버스 정류장 이름 검색
    @GetMapping(value = "/api/busStop/{searchKeyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity search(
            @PathVariable("searchKeyword") String searchKeyword,
            @RequestParam(name = "page", defaultValue = "0") int page
    ) {

        if (page < 0) {
            throw new IllegalArgumentException();
        }

        return ResponseEntity.ok(busStopService.search(searchKeyword, page));
    }


    // 버스 정류장 지도 정보 제공
     @GetMapping(value = "/api/busStop/{busStopId}/map/{zoomLevel}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity mapTest(
            @PathVariable("busStopId") Long busStopId,
            @PathVariable("zoomLevel") Integer zoomLevel
     ) {

        if (zoomLevel < 0 || zoomLevel > 20) {
            throw new IllegalArgumentException();
        }

        byte[] mapImage = busStopService.getBusStopMapImage(busStopId, zoomLevel);
        return ResponseEntity.ok(mapImage);
    }
}
