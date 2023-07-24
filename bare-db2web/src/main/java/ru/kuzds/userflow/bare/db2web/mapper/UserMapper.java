package ru.kuzds.userflow.bare.db2web.mapper;

import org.springframework.stereotype.Component;
import ru.kuzds.userflow.bare.db2web.repository.entity.NewUser;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.User;

@Component
public class UserMapper {

    public SaveUserRequest toDto(NewUser newUser) {
        SaveUserRequest request = new SaveUserRequest();
        User user = new User();
        user.setId(newUser.getId());
        user.setEmail(newUser.getEmail());
        user.setTransferType(newUser.getTransferType());

        request.setUser(user);
        return request;
    }
}
