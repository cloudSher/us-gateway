package com.us.auth.controller;

import com.us.auth.storage.model.User;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/1/9.
 */
@RestController
@Api(value = "auth",description = "auth",position = 1)
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping("/auth")
    public String auth(@AuthenticationPrincipal User user){

        return null;
    }
}
