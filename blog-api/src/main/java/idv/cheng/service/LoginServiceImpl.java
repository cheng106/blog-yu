package idv.cheng.service;

import com.alibaba.fastjson.JSON;
import idv.cheng.enums.ActivityStatus;
import idv.cheng.enums.DeleteStatus;
import idv.cheng.enums.ErrorCode;
import idv.cheng.dao.pojo.SysUser;
import idv.cheng.utils.JwtUtils;
import idv.cheng.vo.Result;
import idv.cheng.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author cheng
 * @since 2021/12/4 18:32
 **/
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private static final String slat = "!@sdf#$";

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result login(LoginParams loginParams) {
        // 檢查參數是否合法
        // 根據帳號和密碼查詢，不存在則登入失敗
        // 存在則使用JWT產生Token給前端
        // 將Token存在Redis，Token:user訊息並設定逾期時間
        // 登入認證時，先認證Token是否合法再去Redis認證是否存在(減少DB查詢)
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if (StringUtils.isAnyBlank(account, password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }

        password = DigestUtils.md5Hex(password + slat);
        System.out.println("password = " + password);
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST);
        }

        String token = JwtUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue()
                .set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isNotBlank(token)) {
            Map<String, Object> map = JwtUtils.checkToken(token);
            if (map != null) {
                String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
                if (StringUtils.isNotBlank(userJson)) {
                    return JSON.parseObject(userJson, SysUser.class);
                }
            }
        }
        return null;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams loginParams) {
        // 判斷參數是否正確
        // 帳號是否存在，存在就不能註冊
        // 不存在才可註冊，並產生Token
        // 放到Redis，返回成功
        // ps. 如果中間有出現任何異常，則註冊的帳號需要Rollback (@Transactional)
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        if (StringUtils.isAnyBlank(account, password, nickname)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST);
        } else {
            sysUser = new SysUser();
            sysUser.setAccount(account);
            sysUser.setNickname(nickname);
            sysUser.setPassword(DigestUtils.md5Hex(password));
            sysUser.setAvatar("/static/img/defaultAvatar.png");
            sysUser.setAdmin(ActivityStatus.INACTIVE.getCode());
            sysUser.setDeleted(DeleteStatus.UNDELETED.getCode());
            sysUser.setStatus(ActivityStatus.ACTIVE.name());
            sysUser.setCreateDate(System.currentTimeMillis());
            sysUser.setLastLogin(System.currentTimeMillis());
            sysUserService.save(sysUser);
            String token = JwtUtils.createToken(sysUser.getId());
            redisTemplate.opsForValue()
                    .set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
            return Result.success(token);
        }
    }
}
