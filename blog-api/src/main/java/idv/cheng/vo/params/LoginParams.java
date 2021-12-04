package idv.cheng.vo.params;

import lombok.Data;

/**
 * @author cheng
 * @since 2021/12/4 18:30
 **/
@Data
public class LoginParams {
    private String account;
    private String password;
    private String nickname;
}
