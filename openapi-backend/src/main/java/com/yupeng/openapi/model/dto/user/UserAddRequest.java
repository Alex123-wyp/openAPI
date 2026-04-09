package com.yupeng.openapi.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * User creation request
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * User nickname
     */
    private String userName;

    /**
     * Account
     */
    private String userAccount;

    /**
     * User avatar
     */
    private String userAvatar;

    /**
     * User role: user, admin
     */
    private String userRole;

    /**
     * access key
     */
    private String accessKey;

    /**
     * secret key
     */
    private String secretKey;

    private static final long serialVersionUID = 1L;
}
