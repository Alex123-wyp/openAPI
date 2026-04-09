package com.yupeng.openapi.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * User update-my request
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@Data
public class UserUpdateMyRequest implements Serializable {

    /**
     * User nickname
     */
    private String userName;

    /**
     * User avatar
     */
    private String userAvatar;

    /**
     * Profile
     */
    private String userProfile;

    private static final long serialVersionUID = 1L;
}