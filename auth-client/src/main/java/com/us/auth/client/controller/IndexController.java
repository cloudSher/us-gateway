package com.us.auth.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/2/10.
 */

@RestController
public class IndexController {


    @RequestMapping("/login")
    public String auth(){

        return "";
    }
}
