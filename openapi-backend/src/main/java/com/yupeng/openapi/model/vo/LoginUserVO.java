package com.yupeng.openapi.model.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Logged-in user view (sanitized)
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 **/
@Data
public class LoginUserVO implements Serializable {

    /**
     * User id
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
     * User profile
     */
    private String userProfile;

    /**
     * User role: user/admin/ban
     */
    private String userRole;

    /**
     * Created time
     */
    private Date createTime;

    /**
     * Updated time
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}