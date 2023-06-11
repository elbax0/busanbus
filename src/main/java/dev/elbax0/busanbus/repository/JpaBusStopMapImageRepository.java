package dev.elbax0.busanbus.repository;

import dev.elbax0.busanbus.domain.BusStop;
import dev.elbax0.busanbus.domain.BusStopMapImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaBusStopMapImageRepository extends JpaRepository<BusStopMapImage, Long> {

    @Query("SELECT img " +
            "FROM BusStopMapImage img " +
            "WHERE img.busStop = :busStop " +
            "   and img.gps_x = :gpsX " +
            "   and img.gps_y = :gpsY " +
            "   and img.zoomLevel = :zoomLevel")
    Optional<BusStopMapImage> findByBusStopAndZoomLevel(
            @Param("busStop") BusStop busStop,
            @Param("gpsX") String gpsX,
            @Param("gpsY") String gpsY,
            @Param("zoomLevel") int zoomLevel
    );
}
