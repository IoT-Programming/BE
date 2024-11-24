package iot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Getter
public enum StatusStandard {
    TEENAGER(10,70, 100, 36.1, 37.2),
    ADULT(20, 60, 100, 36.1, 37.2),
    SENIOR(65, 60, 100, 35.8, 36.9);

    int age;
    double bpmMin;
    double bpmMax;
    double bodyMin;
    double bodyMax;
    final double summerAir = 30;
    final double winterAir = 5;
}
