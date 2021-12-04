package idv.cheng.controller;

import idv.cheng.service.LoginService;
import idv.cheng.vo.Result;
import idv.cheng.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cheng
 * @since 2021/12/4 18:26
 **/
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParams loginParams) {
        // 登入時要驗證使用者
        return loginService.login(loginParams);
    }

}
