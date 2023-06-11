package dev.elbax0.busanbus.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bus_stop")
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusStop extends BaseEntity {

    @Id
    @Column(name = "bus_stop_id")
    private Long busStopId;

    @Column(name = "bus_stop_name")
    private String busStopName;

    @Column(name = "arsno")
    private Long arsno;

    @Column(name = "gps_x")
    private String gpsX;

    @Column(name = "gps_y")
    private String gpsY;

    @Column(name = "stop_type")
    private String stopType;

    @Builder
    public BusStop(Long busStopId, String busStopName, Long arsno, String gpsX, String gpsY, String stopType) {
        this.busStopId = busStopId;
        this.busStopName = busStopName;
        this.arsno = arsno;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.stopType = stopType;
    }
}
