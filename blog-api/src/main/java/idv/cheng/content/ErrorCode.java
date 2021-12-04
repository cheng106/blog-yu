package idv.cheng.content;

import lombok.Getter;

/**
 * @author cheng
 * @since 2021/12/4 19:00
 **/
@Getter
public enum ErrorCode {

    PARAMS_ERROR(10001, "參數錯誤"),
    ACCOUNT_PWD_NOT_EXIST(10002, "帳號或密碼不存在"),
    NO_PERMISSION(70001, "無訪問權限"),
    SESSION_TIMEOUT(90001, "已逾時"),
    NO_LOGIN(90002, "尚未登入"),
    ;

    private final int code;
    private final String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
