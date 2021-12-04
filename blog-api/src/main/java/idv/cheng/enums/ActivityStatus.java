package idv.cheng.enums;

import lombok.Getter;

/**
 * @author cheng
 * @since 2021/12/4 23:01
 **/
@Getter
public enum ActivityStatus {
    ACTIVE(1),
    INACTIVE(0);

    private final int code;

    ActivityStatus(int code) {
        this.code = code;
    }

}
