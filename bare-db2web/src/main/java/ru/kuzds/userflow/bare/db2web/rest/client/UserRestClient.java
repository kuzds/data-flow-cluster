package ru.kuzds.userflow.bare.db2web.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.SaveUserResponse;

@FeignClient(name = "user-client", url = "http://localhost:3333/rest/user" )
public interface UserRestClient {

    @PostMapping(produces = "application/json")
    SaveUserResponse saveUser(SaveUserRequest request);
}
