package com.yupeng.openapi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupeng.openapi.model.dto.auth.UserAuthInfo;
import com.yupeng.openapi.model.dto.user.UserQueryRequest;
import com.yupeng.openapi.model.entity.User;
import com.yupeng.openapi.model.vo.LoginUserVO;
import com.yupeng.openapi.model.vo.UserVO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * User service
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
public interface UserService extends IService<User> {

    /**
     * User registration
     *
     * @param userAccount   User account
     * @param userPassword  User password
     * @param checkPassword Password confirmation
     * @return New user id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * User login
     *
     * @param userAccount  User account
     * @param userPassword User password
     * @param request
     * @return Sanitized user information
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * Get the current logged-in user
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * Get the current logged-in user (allow null)
     *
     * @param request
     * @return
     */
    User getLoginUserPermitNull(HttpServletRequest request);

    /**
     * Is admin
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * Is admin
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    /**
     * User logout
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * Get sanitized logged-in user information
     *
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * Get sanitized user information
     *
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * Get sanitized user information
     *
     * @param userList
     * @return
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * Get query conditions
     *
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * Fetch the auth information used by the gateway to validate signatures.
     *
     * @param accessKey caller access key
     * @return matching auth info, or null if the access key does not exist
     */
    UserAuthInfo getUserAuthInfoByAccessKey(String accessKey);

}
