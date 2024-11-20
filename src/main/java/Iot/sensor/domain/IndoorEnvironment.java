package Iot.sensor.domain;
import jakarta.persistence.*;

@Entity
public class IndoorEnvironment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temperature; // 실내 온도
    private double humidity; // 실내 습도
}
