package ru.kuzds.userflow.bare.db2web.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.SaveUserResponse;

@FeignClient("${userflow.target-service-name}") // using name registered in Eureka
public interface UserRestClient {

    @PostMapping(path = "/rest/user", produces = MediaType.APPLICATION_JSON_VALUE)
    SaveUserResponse saveUser(SaveUserRequest request);
}

