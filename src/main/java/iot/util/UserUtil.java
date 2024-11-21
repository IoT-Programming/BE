package iot.util;

import iot.dto.SensorDto;
import iot.repository.UserRepository;
import iot.domain.User;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static iot.util.StatusStandard.*;

@Component
public class UserUtil {
    @Autowired
    private UserRepository userRepository;

    public StatusStandard statusStandard;

    public UserUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User findUser(String name){
        return userRepository.findUserByName(name);
    }
    public void switchStatus(User user, int overNum){
        user.setStatus(overNum);
        userRepository.save(user);
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
