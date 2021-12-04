package idv.cheng.service;

import com.alibaba.fastjson.JSON;
import idv.cheng.content.ErrorCode;
import idv.cheng.dao.pojo.SysUser;
import idv.cheng.utils.JwtUtils;
import idv.cheng.vo.Result;
import idv.cheng.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author cheng
 * @since 2021/12/4 18:32
 **/
@Service
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
}
