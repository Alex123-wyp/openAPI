package com.yupi.openapipublicinterface.controller;


import cn.hutool.http.server.HttpServerRequest;
import com.yupi.openapipublicinterface.modal.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Name API
 * @author yupewan(Alex Wang)
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/")
    public String getNameByGet(@RequestParam String name) {
        return "GET: Your name is " + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "POST: Your name is " + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServerRequest request) {
        String accessKey = request.getHeader("X-Access-Key");
        String nonce = request.getHeader("X-Nonce");
        String timestamp = request.getHeader("X-Timestamp");
        String sign = request.getHeader("X-Sign");
        String body = request.getBody();
        if(!accessKey.equals("test_user_1775087131_22db19")){
            throw new RuntimeException("Invalid access key");
        }
        if(Long.parseLong(nonce) > 10000){
            throw new RuntimeException("No authentication!");
        }
        long currentTimestamp = System.currentTimeMillis() / 1000;
        long requestTimestamp = Long.parseLong(timestamp);
        if (Math.abs(currentTimestamp - requestTimestamp) > 5 * 60) {
            throw new RuntimeException("Timestamp exceeds 5 minutes");
        }
        return "POST: Your username is " + user.getName();
    }
}
