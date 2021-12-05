package idv.cheng.controller;

import idv.cheng.dao.pojo.SysUser;
import idv.cheng.utils.UserThreadLocal;
import idv.cheng.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cheng
 * @since 2021/12/5 00:07
 **/
@RestController
public class TestController {

    @GetMapping("test")
    public Result test() {
        SysUser user = UserThreadLocal.get();
        System.out.println("user = " + user);
        return Result.success(null);
    }
}
