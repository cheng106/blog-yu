package idv.cheng.service;

import idv.cheng.dao.pojo.SysUser;
import idv.cheng.vo.Result;
import idv.cheng.vo.params.LoginParams;

/**
 * @author cheng
 * @since 2021/12/4 18:29
 **/
public interface LoginService {

    /**
     * 登入功能
     *
     * @param loginParams 登入參數
     * @return idv.cheng.vo.Result
     **/
    Result login(LoginParams loginParams);

    SysUser checkToken(String token);

    Result logout(String token);

    Result register(LoginParams loginParams);
}
