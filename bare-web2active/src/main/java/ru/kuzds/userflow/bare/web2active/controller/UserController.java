package ru.kuzds.userflow.bare.web2active.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuzds.userflow.bare.web2active.service.RabbitService;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.SaveUserResponse;
import ru.kuzds.userflow.userservice.User;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/rest/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final RabbitService rabbitService;

    @PostMapping()
    public SaveUserResponse saveUser(@RequestBody SaveUserRequest request) {
        User user = request.getUser();
        log.info("Received user using REST: id={} email={}", user.getId(), user.getEmail());

        user.setId(ThreadLocalRandom.current().nextInt(0, 10000));
        SaveUserResponse saveUserResponse = new SaveUserResponse();
        saveUserResponse.setUser(user);

        rabbitService.send(user);

        return saveUserResponse;
    }
}
