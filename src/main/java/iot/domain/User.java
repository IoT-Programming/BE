package iot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String gender;
    private int age;
    private String phone;
    private String address;
    private int status; // 0: 적절 1: 관심 2: 주의 3: 위험
    private int prevStatus; // 0: 적절 1: 관심 2: 주의 3: 위험
    private String name;
    private double lan;
    private double lon;
}
