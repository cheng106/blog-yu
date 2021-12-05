package idv.cheng.vo;

import lombok.Data;

import java.util.List;

/**
 * @author cheng
 * @since 2021/12/5 17:15
 **/
@Data
public class CommentVo {
    private Long id;
    private UserVo author;
    private String content;
    private List<CommentVo> childrenList;
    private Integer level;
    private UserVo toUser;
    private String createDate;
}
