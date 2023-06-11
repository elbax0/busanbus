package dev.elbax0.busanbus.repository;

import dev.elbax0.busanbus.domain.BusStop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaBusStopRepository extends JpaRepository<BusStop, Long> {

    Page<BusStop> findByBusStopNameContainsOrderByBusStopName(String keyword, Pageable pageable);

    @Query(
            value = "SELECT * " +
                    " FROM bus_stop " +
                    " WHERE MATCH(bus_stop_name) against(:keyword IN BOOLEAN MODE) " +
                    " ORDER BY bus_stop_name ASC, bus_stop_id ASC;",
            countQuery = "SELECT COUNT(*) FROM bus_stop WHERE MATCH(bus_stop_name) against(:keyword IN BOOLEAN MODE)",
            nativeQuery = true
    )
    Page<BusStop> findByBusStopName(@Param("keyword") String keyword, Pageable pageable);
}
