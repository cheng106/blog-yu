package idv.cheng.dao.pojo;

import lombok.Data;

/**
 * @author cheng
 * @since 2021/12/5 18:46
 **/
@Data
public class ArticleTag {
    private Long id;
    private Long articleId;
    private Long tagId;
}
