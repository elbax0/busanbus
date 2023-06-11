package dev.elbax0.busanbus.controller.dto;

import dev.elbax0.busanbus.service.dto.ApiBusArrivalDto;
import dev.elbax0.busanbus.service.dto.ApiBusArrivalResponse;
import dev.elbax0.busanbus.service.dto.ApiBusArrivalResponseBody;
import lombok.Data;
import org.springframework.util.comparator.Comparators;

import java.util.ArrayList;
import java.util.List;

@Data
public class BusArrivalResponse {
    Long busStopId;
    List<ApiBusArrivalDto> busList = new ArrayList<>();

    public BusArrivalResponse(Long busStopId, ApiBusArrivalResponse response) {
        this.busStopId = busStopId;
        this.busList = response.getBody().getItems();
        sortBusList();
    }

    public BusArrivalResponse(ApiBusArrivalResponse response) {
        this(null, response);
    }

    // 버스 번호를 기준으로 오름차순 정렬
    private void sortBusList() {
        if (busList.size() < 2) {
            return;
        }
        
        // 마을 버스 정류장인 경우 단순 문자열 비교로 정렬
        // 마을 버스의 버스 번호는 숫자가 아닌 지역구 이름으로 시작함
        if (!Character.isDigit((busList.get(0).getLineno().charAt(0)))) {
            busList.sort((a, b) -> Comparators.comparable().compare(a.getLineno(), b.getLineno()));
        } else {
            busList.sort((a, b) -> compareLineNo(a.getLineno(), b.getLineno()));
        }
    }

    // 버스 번호 기준으로 오름차순으로 정렬 하기 위해서 버스번호 데이터로부터 숫자 추출 후 비교
    private int compareLineNo(String lineno1, String lineno2) {
        return Double.compare(parseLineNo(lineno1), parseLineNo(lineno2));
    }

    // 버스 번호 기준으로 오름차순 정렬을 위해서 1004(심야), 83-1과 같은 버스번호 데이터로부터 부동소수점 타입 숫자를 추출
    // 1004(심야) -> 1004.0
    // 83-1 -> 83.1
    // 169 -> 169.0
    private double parseLineNo(String lineno) {
        String line = lineno.codePoints()
                .takeWhile(i -> i >= '0' && i <= '9')
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        char sub = lineno.contains("-") ? lineno.charAt(lineno.indexOf('-') + 1) : '0';
        return Double.parseDouble(String.format("%s.%s", line, sub));
    }
}
