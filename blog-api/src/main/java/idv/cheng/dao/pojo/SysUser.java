package idv.cheng.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author cheng
 * @since 2021/12/4 12:08
 **/
@Data
public class SysUser {

    // 未來帳號變多後，要進行分表操作，ID就需要用分散式ID
    @TableId(type = IdType.ASSIGN_ID) // 預設的ID
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
