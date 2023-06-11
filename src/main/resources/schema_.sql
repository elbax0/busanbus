create table bus_stop (
    bus_stop_id         bigint primary key,
    arsno               bigint,
    bus_stop_name       varchar(256),
    gps_x               varchar(32),
    gps_y               varchar(32),
    stop_type           varchar(32),
    created_at          datetime
) engine = innodb charset=utf8mb4;

ALTER TABLE bus_stop ADD FULLTEXT INDEX idx_ft_bus_stop_name (bus_stop_name) WITH PARSER NGRAM;


create table bus_stop_map_image (
    id                  bigint auto_increment primary key,
    bus_stop_id         bigint,
    gps_x               varchar(32),
    gps_y               varchar(32),
    zoom_level          int,
    binary_data         longblob,
    format              varchar(16),
    created_at          datetime
) engine = innodb charset=utf8mb4;
