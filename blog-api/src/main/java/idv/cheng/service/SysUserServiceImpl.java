package idv.cheng.service;

import idv.cheng.dao.mapper.SysUserMapper;
import idv.cheng.dao.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author cheng
 * @since 2021/12/4 13:31
 **/
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper mapper;

    @Override
    public SysUser findUserById(Long id) {
        return Optional.ofNullable(mapper.selectById(id)).orElse(new SysUser());
    }
}
