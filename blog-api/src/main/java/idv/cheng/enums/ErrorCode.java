package idv.cheng.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author cheng
 * @since 2021/12/4 19:00
 **/
@Getter
public enum ErrorCode {

    PARAMS_ERROR(10001, "參數錯誤"),
    ACCOUNT_PWD_NOT_EXIST(10002, "帳號或密碼不存在"),
    PartnerAPIAccessFailed(10003, "API執行失敗", HttpStatus.INTERNAL_SERVER_ERROR),
    PartnerAPIResolveFailed(10004, "API解析失敗", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCOUNT_EXIST(10005, "帳號已存在"),
    NO_PERMISSION(70001, "無訪問權限", HttpStatus.UNAUTHORIZED),
    SESSION_TIMEOUT(90001, "已逾時"),
    NO_LOGIN(90002, "尚未登入"),
    TOKEN_ERROR(10003, "TOKEN 驗證失敗", HttpStatus.UNAUTHORIZED),
    ;

    private final int code;
    private final String msg;
    private final HttpStatus status;

    ErrorCode(int code, String msg, HttpStatus status) {
        this.code = code;
        this.msg = msg;
        this.status = status;
    }

    ErrorCode(int code, String msg) {
        this(code, msg, HttpStatus.BAD_REQUEST);
    }
}
