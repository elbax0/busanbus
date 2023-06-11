package dev.elbax0.busanbus.service.dto;

import lombok.Data;

@Data
public class ApiBusStopResponse {
    ApiResponseHeader header;
    ApiBusStopResponseBody body;
}
