package idv.cheng.dao.pojo;

import lombok.Data;

/**
 * @author cheng
 * @since 2021/12/5 15:48
 **/
@Data
public class ArticleBody {
    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
