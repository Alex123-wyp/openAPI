package com.yupeng.openapi.model.dto.user;

import com.yupeng.openapi.common.PageRequest;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User query request
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * Open platform id
     */
    private String unionId;

    /**
     * Official account openId
     */
    private String mpOpenId;

    /**
     * User nickname
     */
    private String userName;

    /**
     * Profile
     */
    private String userProfile;

    /**
     * User role: user/admin/ban
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}