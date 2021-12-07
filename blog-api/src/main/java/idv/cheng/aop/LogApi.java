package idv.cheng.aop;

import java.lang.annotation.*;

/**
 * @author cheng
 * @since 2021/12/6 20:15
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogApi {

    String module() default "";

    String operator() default "";
}
