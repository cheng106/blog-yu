package idv.cheng.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import idv.cheng.dao.mapper.ArticleMapper;
import idv.cheng.dao.pojo.Article;
import idv.cheng.vo.ArticleVo;
import idv.cheng.vo.Result;
import idv.cheng.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cheng
 * @since 2021/12/4 12:23
 **/
@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleService;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result listArticle(PageParams params) {
        Page<Article> page = new Page<>(params.getPage(), params.getPageSize());
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 是否置頂進行排序
        wrapper.orderByDesc(Article::getWeight);
        // order by create_date desc;
        wrapper.orderByDesc(Article::getCreateDate);
        Page<Article> articlePage = articleService.selectPage(page, wrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> articleVos = copyList(records, true, true);
        return Result.success(articleVos);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articles = new ArrayList<>();
        records.forEach(r -> articles.add(copy(r, isTag, isAuthor)));
        return articles;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        // 並非所有作者都要標籤和訊息
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        return articleVo;
    }
}
