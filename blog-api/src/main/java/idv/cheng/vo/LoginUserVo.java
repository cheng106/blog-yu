package idv.cheng.vo;

import lombok.Data;

/**
 * @author cheng
 * @since 2021/12/4 21:28
 **/
@Data
public class LoginUserVo {
    private Long id;
    private String account;
    private String nickname;
    private String avatar;
}
