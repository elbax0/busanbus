package dev.elbax0.busanbus.service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiBusArrivalResponseBody {
    List<ApiBusArrivalDto> items = new ArrayList<>();
}
