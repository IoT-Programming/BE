package iot.util;

import iot.repository.UserRepository;
import iot.domain.User;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserUtil {
    @Autowired
    private UserRepository userRepository;

    public User findUser(String name){
        return userRepository.findUserByName(name);
    }
    public void switchStatus(User user){
        String userName = user.getName();
        int currentStatus = userRepository.findStatusByName(userName);
        user.setStatus((currentStatus + 1) % 3);
        userRepository.save(user);
    }
}
