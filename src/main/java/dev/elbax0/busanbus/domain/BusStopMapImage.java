package dev.elbax0.busanbus.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bus_stop_map_image",
        uniqueConstraints = @UniqueConstraint(columnNames = {"bus_stop_id", "gps_x", "gps_y", "zoom_level"})
)
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusStopMapImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_stop_id")
    private BusStop busStop;

    @Column(name = "gps_x")
    private String gps_x;

    @Column(name = "gps_y")
    private String gps_y;

    @Column(name = "zoom_level")
    private Integer zoomLevel;

    @Lob
    @Column(name = "binary_data", columnDefinition = "longblob")
    private byte[] binaryData;

    @Column(name = "format")
    private String format;

    public BusStopMapImage(BusStop busStop, int zoomLevel, byte[] imageData) {
        this.busStop = busStop;
        this.gps_x = busStop.getGpsX();
        this.gps_y = busStop.getGpsY();
        this.zoomLevel = zoomLevel;
        this.binaryData = imageData;
        this.format = "png";
    }
}
