package iot.dto;

import lombok.Getter;

@Getter
public class SensorDto {
    public double BPM;
    public double bodyTemp;
    public double airTemp;
    public double lan;
    public double lon;
}
