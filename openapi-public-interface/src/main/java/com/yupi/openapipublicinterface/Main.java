package com.yupi.openapipublicinterface;

import com.yupi.openapipublicinterface.client.OpenApiClient;
import com.yupi.openapipublicinterface.modal.User;

/**
 * Simple entry point for manually testing OpenApiClient.
 */
public class Main {

    public static void main(String[] args) {
        OpenApiClient openApiClient = new OpenApiClient();

        String getResult = openApiClient.getNameByGet("Alex");
        System.out.println("GET result: " + getResult);

        String postResult = openApiClient.getNameByPost("Alex");
        System.out.println("POST result: " + postResult);

        User user = new User();
        user.setName("Alex");
        String postUserResult = openApiClient.getUserNameByPost(user);
        System.out.println("POST user result: " + postUserResult);

        
    }
}
