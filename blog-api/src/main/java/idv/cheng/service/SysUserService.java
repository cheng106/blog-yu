package idv.cheng.service;

import idv.cheng.dao.pojo.SysUser;
import idv.cheng.vo.Result;
import idv.cheng.vo.UserVo;

/**
 * @author cheng
 * @since 2021/12/4 13:31
 **/
public interface SysUserService {

    UserVo findUserVoById(Long id);

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);
}
