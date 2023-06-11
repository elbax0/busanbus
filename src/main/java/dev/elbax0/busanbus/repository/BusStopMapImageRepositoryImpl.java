package dev.elbax0.busanbus.repository;

import dev.elbax0.busanbus.domain.BusStop;
import dev.elbax0.busanbus.domain.BusStopMapImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BusStopMapImageRepositoryImpl implements BusStopMapImageRepository{

    private final JpaBusStopMapImageRepository jpaBusStopMapImageRepository;

    @Override
    public Optional<BusStopMapImage> getBusStopMapImage(BusStop busStop, int zoomLevel) {
        return jpaBusStopMapImageRepository.findByBusStopAndZoomLevel(busStop, busStop.getGpsX(), busStop.getGpsY(), zoomLevel);
    }

    @Override
    public void save(BusStopMapImage busStopMapImage) {
        jpaBusStopMapImageRepository.save(busStopMapImage);
    }
}
