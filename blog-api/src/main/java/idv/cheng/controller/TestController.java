package idv.cheng.controller;

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
        return Result.success(null);
    }
}
