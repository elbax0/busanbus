package dev.elbax0.busanbus.service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiBusStopResponseBody {
    List<ApiBusStopDto> items = new ArrayList<>();
    Long numOfRows;
    Long pageNo;
    Long totalCount;
}
