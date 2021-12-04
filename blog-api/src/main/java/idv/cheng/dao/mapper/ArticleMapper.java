package idv.cheng.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import idv.cheng.dao.pojo.Article;
import idv.cheng.dao.vo.Archives;

import java.util.List;

/**
 * @author cheng
 * @since 2021/12/4 12:03
 **/
public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArticles();
}
