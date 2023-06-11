package dev.elbax0.busanbus.service;

import dev.elbax0.busanbus.repository.BusStopRepository;
import dev.elbax0.busanbus.service.dto.ApiBusStopDto;
import dev.elbax0.busanbus.service.dto.ApiBusStopResponse;
import dev.elbax0.busanbus.util.XmlParseUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppStartupService implements CommandLineRunner {

    private final BusStopService busStopService;

    @Override
    public void run(String... args) throws Exception {
        // 애플리케이션 시작 시 데이터베이스에 버스 정류장 정보가 없다면 API로부터 가져옴
        busStopService.init();
    }
}
