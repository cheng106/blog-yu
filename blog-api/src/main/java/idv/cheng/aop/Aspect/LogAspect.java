package idv.cheng.aop.Aspect;

import com.alibaba.fastjson.JSON;
import idv.cheng.aop.LogApi;
import idv.cheng.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author cheng
 * @since 2021/12/6 20:17
 **/
@Component
@Aspect // 定義通知和切點的關係
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(idv.cheng.aop.LogApi)")
    public void pt() {
    }

    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - beginTime;
        recordLog(joinPoint, time);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogApi logApi = method.getAnnotation(LogApi.class);
        log.info("===================log start===================");
        log.info("module:{}", logApi.module());
        log.info("operation:{}", logApi.operator());

        // 請求的方法名稱
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        log.info("request method:{}.{}()", className, methodName);

        // 請求的參數
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        log.info("params:{}", params);

        // 取得request設定IP
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            log.warn("{}.{} not found request attribute, return null.", className, methodName);
            return;
        }

        HttpServletRequest request = requestAttributes.getRequest();
        String ip = IpUtils.getRealIP(request);
        log.info("ip:{}", ip);
        log.info("execute time: {} ms", time);
        log.info("===================log end===================");
    }
}
