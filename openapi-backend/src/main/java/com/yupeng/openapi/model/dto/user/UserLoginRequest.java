package com.yupeng.openapi.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * User login request
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;
}
