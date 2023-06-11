package dev.elbax0.busanbus.service.dto;

import dev.elbax0.busanbus.domain.BusStop;
import lombok.Data;

@Data
public class ApiBusStopDto {
    private String bstopid;
    private String bstopnm;
    private String arsno;
    private String gpsx;
    private String gpsy;
    private String stoptype;

    public ApiBusStopDto(BusStop busStop) {
        this.bstopid = String.valueOf(busStop.getBusStopId());
        this.bstopnm = busStop.getBusStopName();
        this.arsno = busStop.getArsno() != null ? String.valueOf(busStop.getArsno()) : null;
        this.gpsx = busStop.getGpsX();
        this.gpsy = busStop.getGpsY();
        this.stoptype = busStop.getStopType();
    }

    public BusStop toEntity() {
        return BusStop.builder()
                .busStopId(Long.parseLong(bstopid))
                .busStopName(bstopnm)
                .arsno(arsno != null ? Long.parseLong(arsno) : null)
                .gpsX(gpsx)
                .gpsY(gpsy)
                .stopType(stoptype)
                .build();
    }
}
