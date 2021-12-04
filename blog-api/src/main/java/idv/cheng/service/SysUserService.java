package idv.cheng.service;

import idv.cheng.dao.pojo.SysUser;

/**
 * @author cheng
 * @since 2021/12/4 13:31
 **/
public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);
}
