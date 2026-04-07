package com.yupi.openapipublicinterface.controller;
import com.yupi.openapiclientsdk.modal.User;
import com.yupi.openapiclientsdk.utils.SignUtils;
import com.yupi.openapipublicinterface.common.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
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
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {

//        String accessKey = request.getHeader("accessKey");
//        String nonce = request.getHeader("nonce");
//        String timestamp = request.getHeader("timestamp");
//        String sign = request.getHeader("sign");
//        String body = request.getHeader("body");
//
//        if(!accessKey.equals("ak_a6ec8ee09db3066c63aebc4980d3bb2b")){
//            throw new RuntimeException("Invalid access key");
//        }
//        if(Long.parseLong(nonce) > 10000){
//            throw new RuntimeException("No authentication!");
//        }
//        long currentTimestamp = System.currentTimeMillis() / 1000;
//        long requestTimestamp = Long.parseLong(timestamp);
//        if (Math.abs(currentTimestamp - requestTimestamp) > 5 * 60) {
//            throw new RuntimeException("Timestamp exceeds 5 minutes");
//        }
//        String serverSign = SignUtils.genSign(body, "sk_954ee91d44875f13f21610af5432f59ba089db9d8c5a4ed8");
//        if(!serverSign.equals(sign)){
//            throw new RuntimeException("Invalid sign");
//        }
        String result = "POST: Your username is " + user.getName();

//        return new BaseResponse<>(200, result, "success");
        return result;

    }
}
