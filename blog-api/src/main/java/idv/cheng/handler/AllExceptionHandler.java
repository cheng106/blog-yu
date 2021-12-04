package idv.cheng.handler;

import idv.cheng.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author cheng
 * @since 2021/12/4 17:20
 **/
// 有@Controller註解的都進行攔截處理 (AOP)
@RestControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex) {
        ex.printStackTrace();
        return Result.fail(-999, "系統異常");
    }
}
