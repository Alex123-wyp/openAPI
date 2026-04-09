package com.yupeng.openapi.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * User update request
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

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

    /**
     * User role: user/admin/ban
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
