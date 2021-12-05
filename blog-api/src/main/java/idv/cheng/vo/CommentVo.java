package idv.cheng.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author cheng
 * @since 2021/12/5 17:15
 **/
@Data
public class CommentVo {

    // 防止前端精度損失，把ID轉成字串
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private UserVo author;
    private String content;
    private List<CommentVo> childrenList;
    private Integer level;
    private UserVo toUser;
    private String createDate;
}
