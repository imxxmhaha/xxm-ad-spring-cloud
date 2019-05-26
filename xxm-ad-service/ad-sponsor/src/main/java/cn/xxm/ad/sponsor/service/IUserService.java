package cn.xxm.ad.sponsor.service;


import cn.xxm.ad.common.exception.AdException;
import cn.xxm.ad.sponsor.vo.CreateUserRequest;
import cn.xxm.ad.sponsor.vo.CreateUserResponse;

/**
 * Created by Qinyi.
 */
public interface IUserService {

    /**
     * <h2>创建用户</h2>
     * */
    CreateUserResponse createUser(CreateUserRequest request)
            throws AdException;
}
