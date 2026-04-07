package com.yupi.openapipublicinterface;

import com.yupi.openapiclientsdk.client.OpenApiClient;
import com.yupi.openapiclientsdk.modal.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenapiPublicInterfaceApplicationTests {



    @Resource
    private OpenApiClient openApiClient;

    @Test
    void contextLoads() {
        String result = openApiClient.getNameByGet("yupi");
        User user = new User();
        user.setName("yupi");
        String userNameBypost = openApiClient.getUserNameByPost(user);
        System.out.println(result);
        System.out.println(userNameBypost);
    }

}
