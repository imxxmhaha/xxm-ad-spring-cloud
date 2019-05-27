package cn.xxm.ad.sponsor.service.impl;



import cn.xxm.ad.api.entity.AdUser;
import cn.xxm.ad.common.constant.Constants;
import cn.xxm.ad.common.exception.AdException;
import cn.xxm.ad.common.exception.ExceptionCast;
import cn.xxm.ad.common.response.CommonCode;
import cn.xxm.ad.common.utils.CommonUtils;
import cn.xxm.ad.common.vo.CreateUserRequest;
import cn.xxm.ad.common.vo.CreateUserResponse;
import cn.xxm.ad.sponsor.dao.AdUserRepository;
import cn.xxm.ad.sponsor.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Qinyi.
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request)
            throws AdException {

        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = userRepository.
                findByUsername(request.getUsername());
        if (oldUser != null) {
            ExceptionCast.cast(CommonCode.SAME_NAME_ERROR);
        }

        AdUser newUser = userRepository.save(new AdUser(
                request.getUsername(),
                CommonUtils.md5(request.getUsername())
        ));

        return new CreateUserResponse(
                newUser.getId(), newUser.getUsername(), newUser.getToken(),
                newUser.getCreateTime(), newUser.getUpdateTime()
        );
    }
}
