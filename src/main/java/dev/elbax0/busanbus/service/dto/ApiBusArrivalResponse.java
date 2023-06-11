package dev.elbax0.busanbus.service.dto;

import dev.elbax0.busanbus.service.dto.ApiBusArrivalResponseBody;
import dev.elbax0.busanbus.service.dto.ApiResponseHeader;
import lombok.Data;

@Data
public class ApiBusArrivalResponse {
    ApiResponseHeader header;
    ApiBusArrivalResponseBody body;
}
