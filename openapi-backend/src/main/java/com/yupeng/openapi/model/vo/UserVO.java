package com.yupeng.openapi.model.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * User view (sanitized)
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@Data
public class UserVO implements Serializable {

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

    private static final long serialVersionUID = 1L;
}