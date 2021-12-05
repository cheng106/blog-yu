package idv.cheng.handler;

import com.alibaba.fastjson.JSON;
import idv.cheng.dao.pojo.SysUser;
import idv.cheng.enums.ErrorCode;
import idv.cheng.service.LoginService;
import idv.cheng.utils.UserThreadLocal;
import idv.cheng.vo.Result;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cheng
 * @since 2021/12/4 23:49
 **/
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在執行Controller之前執行
        // 需要判斷請求的路徑是否為HandlerMethod (Controller方法)
        // 判斷是否有Token
        // 如果有Token則驗證(loginService checkToken)
        // 認證成功就返回true

        if (!(handler instanceof HandlerMethod)) {
            // handler 可能是 RequestResourceHandler
            // springboot 訪問靜態資源，預設去classpath下的static目錄去查詢
            return true;
        }
        String token = request.getHeader("Authorization");

        log.info("==============request start==============");
        String requestUri = request.getRequestURI();
        log.info("request uri:{}", requestUri);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("==============request end==============");

        if (StringUtils.isBlank(token)) {
            return noLoginStatus(response);
        }

        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            return noLoginStatus(response);
        }

        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 如果不刪除 ThreadLocal 中的資料，可能會有記憶體溢位的風險
        UserThreadLocal.remove();
    }

    private boolean noLoginStatus(HttpServletResponse response) throws IOException {
        Result result = Result.fail(ErrorCode.NO_LOGIN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(JSON.toJSONString(result));
        return false;
    }
}
