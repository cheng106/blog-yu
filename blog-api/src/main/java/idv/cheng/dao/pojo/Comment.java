package idv.cheng.dao.pojo;

import lombok.Data;

/**
 * @author cheng
 * @since 2021/12/5 16:53
 **/
@Data
public class Comment {
    private Long id;
    private String content;
    private Long articleId;
    private Long authorId;
    private Long parentId;
    private Long toUid;
    private Integer level;
    private Long createDate;
}
