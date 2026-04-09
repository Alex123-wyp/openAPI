package com.yupeng.openapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInterfaceInfoServiceTest {


    @Resource
    private UserInterfaceInfoService userInterfaceinfoService;

    @Test
    void invokeCount() {
        boolean b = userInterfaceinfoService.invokeCount(1L, 1L);
        Assertions.assertTrue(b);
    }
}