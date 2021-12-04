package idv.cheng.enums;

import lombok.Getter;

/**
 * @author cheng
 * @since 2021/12/4 23:03
 **/
@Getter
public enum DeleteStatus {
    DELETED(1),
    UNDELETED(0),
    ;

    private final int code;

    DeleteStatus(int code) {
        this.code = code;
    }
}
