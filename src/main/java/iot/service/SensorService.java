package iot.service;

import iot.dto.SensorDto;
import iot.domain.User;
import iot.util.StatusStandard;
import iot.util.UserUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static iot.util.StatusStandard.*;

@Service
public class SensorService {
    private StatusStandard statusStandard;
    private UserUtil userUtil;

    public SensorService(UserUtil userUtil){
        this.userUtil = userUtil;
    }

    @Transactional
    public void updateStatus(SensorDto sensorDto, User user){
        int overNum = calculateValue(sensorDto, judgeAge(user));
        userUtil.switchStatus(user, overNum);
    }
    public StatusStandard judgeAge(User user){
        int age = user.getAge();
        if(age >=10 && age<20){
            statusStandard = TEENAGER;
        }
        else if(age>=20 && age <65){
            statusStandard = ADULT;
        }
        else if(age >= 65){
            statusStandard = SENIOR;
        }
        return statusStandard;
    }
    public int calculateValue(SensorDto sensorDto, StatusStandard statusStandard) {
        int isOver = 0;
        LocalDate ld = LocalDate.now();
        int month = ld.getMonthValue();
        if (month >= 6 && month <= 9) { // 온열질환
            if (sensorDto.getAirTemp() >= statusStandard.getSummerAir()) {
                isOver += 1;
            }
            if (sensorDto.getBodyTemp() >= statusStandard.getBodyMax()) {
                isOver += 1;
            }
            if (sensorDto.getPulse() >= statusStandard.getBpmMax()) {
                isOver += 1;
            }
        } else if (month >= 11 || month <= 3) { // 한랭질환
            if (sensorDto.getAirTemp() <= statusStandard.getSummerAir()) {
                isOver += 1;
            }
            if (sensorDto.getBodyTemp() <= statusStandard.getBodyMax()) {
                isOver += 1;
            }
            if (sensorDto.getPulse() <= statusStandard.getBpmMax()) {
                isOver += 1;
            }
        }
        return isOver;
    }
}
