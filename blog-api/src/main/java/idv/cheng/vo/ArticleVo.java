package idv.cheng.vo;

import lombok.Data;

import java.util.List;

/**
 * @author cheng
 * @since 2021/12/4 12:30
 **/
@Data
public class ArticleVo {
    private Long id;
    private String title;
    private String summary;
    private int commentCounts;
    private int viewCounts;
    private int weight;
    private String createDate;
    private String author;
    private List<TagVo> tags;
    private ArticleBodyVo body;
    private CategoryVo category;
}
