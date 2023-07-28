package ru.kuzds.userflow.bare.web2rabbit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuzds.userflow.bare.web2rabbit.mapper.UserMapper;
import ru.kuzds.userflow.bare.web2rabbit.service.RabbitService;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.SaveUserResponse;
import ru.kuzds.userflow.userservice.User;

@RestController
@RequestMapping("/rest/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final RabbitService rabbitService;
    private final UserMapper userMapper;

    @PostMapping()
    public SaveUserResponse saveUser(@RequestBody SaveUserRequest request) {
        User user = request.getUser();
        log.info("Sending {}", userMapper.toString(user));

        rabbitService.send(user);

        return userMapper.toSaveUserResponse(request);
    }
}
