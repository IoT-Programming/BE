package iot.util;

import iot.dto.SensorDto;
import iot.repository.UserRepository;
import iot.domain.User;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static iot.util.StatusStandard.*;

@Component
public class UserUtil {
    @Autowired
    private final UserRepository userRepository;

    public StatusStandard statusStandard;

    public UserUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User findUser(String name){
        return userRepository.findUserByName(name);
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
        if (month >= 6 && month <= 9) {
            if (sensorDto.getAirTemp() >= statusStandard.getSummerAir()) {
                isOver += 1;
            }
            if (sensorDto.getBodyTemp() >= statusStandard.getBodyMax()) {
                isOver += 1;
            }
            if (sensorDto.getBPM() >= statusStandard.getBpmMax()) {
                isOver += 1;
            }
        } else if (month >= 11 || month <= 3) {
            if (sensorDto.getAirTemp() <= statusStandard.getWinterAir()) {
                isOver += 1;
            }
            if (sensorDto.getBodyTemp() <= statusStandard.getBodyMin()) {
                isOver += 1;
            }
            if (sensorDto.getBPM() <= statusStandard.getBpmMin()) {
                isOver += 1;
            }
        }
        return isOver;
    }
}
