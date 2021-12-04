package idv.cheng.dao.pojo;

import lombok.Data;

/**
 * @author cheng
 * @since 2021/12/4 12:01
 **/
@Data
public class Article {
    public static final int ARTICLE_TOP = 1;
    public static final int ARTICLE_COMMON = 0;
    private Long id;
    private String title;
    private String summary;
    private Long authorId;
    private Long bodyId;
    private Long categoryId;
    private int commentCounts;
    private int viewCounts;
    private int weight = ARTICLE_COMMON;
    private Long createDate;
}
