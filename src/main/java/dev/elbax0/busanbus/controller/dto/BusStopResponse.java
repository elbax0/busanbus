package dev.elbax0.busanbus.controller.dto;

import dev.elbax0.busanbus.domain.BusStop;
import dev.elbax0.busanbus.service.dto.ApiBusStopDto;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
public class BusStopResponse {
    private String searchKeyword;
    private List<ApiBusStopDto> busStopList = new ArrayList<>();
    private int numOfElements;
    private int totalPage;
    private int currPage;

    public BusStopResponse(String searchKeyword, Page<BusStop> data) {
        this.searchKeyword = searchKeyword;
        this.busStopList = data.stream().map(ApiBusStopDto::new).toList();
        this.numOfElements = data.getNumberOfElements();
        this.totalPage = data.getTotalPages();
        this.currPage = data.getNumber();
    }
}
