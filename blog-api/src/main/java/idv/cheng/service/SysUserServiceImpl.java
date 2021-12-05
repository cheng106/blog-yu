package idv.cheng.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import idv.cheng.enums.ErrorCode;
import idv.cheng.dao.mapper.SysUserMapper;
import idv.cheng.dao.pojo.SysUser;
import idv.cheng.vo.LoginUserVo;
import idv.cheng.vo.Result;
import idv.cheng.vo.UserVo;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private LoginService loginService;

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = Optional.ofNullable(sysUserMapper.selectById(id)).orElse(new SysUser());
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser, userVo);
        return userVo;
    }

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

    @Override
    public Result findUserByToken(String token) {
        // 檢查Token，解析是否成功，Redis是否存在
        // 失敗返回錯誤
        // 成功返回特定VO
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            return Result.fail(ErrorCode.TOKEN_ERROR);
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        BeanUtils.copyProperties(sysUser, loginUserVo);
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getAccount, account);
        wrapper.last("limit 1");
        return sysUserMapper.selectOne(wrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        // 儲存User，Id會自動產生
        // 但使用Mybatis-plus，預設的ID是分散式ID (雪花演算法)
        sysUserMapper.insert(sysUser);
    }
}
