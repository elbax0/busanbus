package dev.elbax0.busanbus.repository;

import dev.elbax0.busanbus.domain.BusStop;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class BusStopRepositoryImpl implements BusStopRepository {

    private final JpaBusStopRepository jpaBusStopRepository;

    @Override
    public void saveAll(Iterable<BusStop> list) {
        jpaBusStopRepository.saveAll(list);
    }

    @Override
    public Page<BusStop> findByKeyword(String keyword, Pageable pageable) {
        return jpaBusStopRepository.findByBusStopName(conversionForFulltextSearch(keyword), pageable);
    }

    @Override
    public BusStop findByBusStopId(Long id) {
        return jpaBusStopRepository.findById(id).orElseThrow();
    }

    // Full-Text Index 검색을 적용하기 위한 전처리
    // 검색 키워드를 공백 단위로 분리하고 +를 붙인뒤 결합
    private String conversionForFulltextSearch(String keyword) {
        return Arrays.stream(keyword.split(" "))
                .map(str -> "+" + str)
                .collect(Collectors.joining(" "));
    }

    @Override
    public Long count() {
        return jpaBusStopRepository.count();
    }
}
