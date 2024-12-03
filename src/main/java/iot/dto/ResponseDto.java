package iot.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDto {
    public double bpm;
    public double bodyTemp;
    public double airTemp;
    public double lan;
    public double lon;

    @Nullable
    public String message;
    @Nullable
    public String phone;


}
