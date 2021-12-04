package idv.cheng.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long id) {
        return Optional.ofNullable(sysUserMapper.selectById(id)).orElse(new SysUser());
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getAccount, account);
        wrapper.eq(SysUser::getPassword, password);
        wrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAvatar, SysUser::getNickname);
        wrapper.last("limit 1");
        return sysUserMapper.selectOne(wrapper);
    }
}
