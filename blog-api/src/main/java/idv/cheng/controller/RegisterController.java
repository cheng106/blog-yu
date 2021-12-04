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
 * @since 2021/12/4 22:42
 **/
@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParams loginParams) {
        // sso 單一登入，未來如果把登入註冊功能拉出去做，可以獨立提供服務
        return loginService.register(loginParams);
    }
}
