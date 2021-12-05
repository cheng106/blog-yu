package idv.cheng.vo.params;

import idv.cheng.vo.CategoryVo;
import idv.cheng.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author cheng
 * @since 2021/12/5 18:35
 **/
@Data
public class ArticleParam {
    private Long id;
    private ArticleBodyParam body;
    private CategoryVo categoryVo;
    private String summary;
    private List<TagVo> tags;
    private String title;
}
