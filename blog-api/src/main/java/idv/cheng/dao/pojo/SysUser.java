package idv.cheng.dao.pojo;

import lombok.Data;

/**
 * @author cheng
 * @since 2021/12/4 12:08
 **/
@Data
public class SysUser {
    private Long id;
    private String account;
    private String password;
    private String salt;
    private String mobilePhoneNumber;
    private Integer admin;
    private String nickname;
    private String email;
    private String avatar;
    private Integer deleted;
    private Long createDate;
    private Long lastLogin;
    private String status;
}
