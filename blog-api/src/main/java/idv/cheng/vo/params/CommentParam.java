package idv.cheng.vo.params;

import lombok.Data;

/**
 * @author cheng
 * @since 2021/12/5 18:04
 **/
@Data
public class CommentParam {
    private Long articleId;
    private Long parent;
    private Long toUserId;
    private String content;
}
