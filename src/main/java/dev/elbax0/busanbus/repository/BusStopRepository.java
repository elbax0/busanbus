package dev.elbax0.busanbus.repository;

import dev.elbax0.busanbus.domain.BusStop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BusStopRepository {

    public void saveAll(Iterable<BusStop> list);

    public Page<BusStop> findByKeyword(String keyword, Pageable pageable);

    public BusStop findByBusStopId(Long id);

    public Long count();
}
