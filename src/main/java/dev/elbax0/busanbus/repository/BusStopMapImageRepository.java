package dev.elbax0.busanbus.repository;

import dev.elbax0.busanbus.domain.BusStop;
import dev.elbax0.busanbus.domain.BusStopMapImage;

import java.util.Optional;

public interface BusStopMapImageRepository {

    Optional<BusStopMapImage> getBusStopMapImage(BusStop busStop, int zoomLevel);

    void save(BusStopMapImage busStopMapImage);
}
