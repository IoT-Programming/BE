package Iot.sensor.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Member")
@Getter
@Setter
@NoArgsConstructor
public class Member
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 대상 이름

    @Column(nullable = false)
    private String contact; // 보호자 연락처

    @Column(nullable = false)
    private String x;//x좌표
    @Column(nullable = false)
    private String y;//y좌표

    @Column(nullable = false)
    private int status;

    private double bodyTemperature; // 체온
    private int heartRate; // 심박수
    private LocalDateTime lasttime;

}
