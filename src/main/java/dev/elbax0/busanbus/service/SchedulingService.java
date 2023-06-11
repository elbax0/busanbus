package dev.elbax0.busanbus.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final BusArrivalService busArrivalService;

    // 매주 월요일 03시에 버스 정류장 정보 업데이트
    @Scheduled(cron = "0 0 3 * * MON", zone = "Asia/Seoul")
    public void updateBusStopInfo() {
        // TODO
    }

    // 6초 마다 각 버스 정류장 도착 정보 업데이트
    @Scheduled(fixedRate = 6000)
    public void updateBusArrival() {
        busArrivalService.updateArrivalData();
    }

}
