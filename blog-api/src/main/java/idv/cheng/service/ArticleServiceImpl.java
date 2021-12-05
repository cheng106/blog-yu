package idv.cheng.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import idv.cheng.dao.mapper.ArticleBodyMapper;
import idv.cheng.dao.mapper.ArticleMapper;
import idv.cheng.dao.mapper.ArticleTagMapper;
import idv.cheng.dao.pojo.Article;
import idv.cheng.dao.pojo.ArticleBody;
import idv.cheng.dao.pojo.ArticleTag;
import idv.cheng.dao.pojo.SysUser;
import idv.cheng.dao.vo.Archives;
import idv.cheng.utils.UserThreadLocal;
import idv.cheng.vo.ArticleBodyVo;
import idv.cheng.vo.ArticleVo;
import idv.cheng.vo.Result;
import idv.cheng.vo.TagVo;
import idv.cheng.vo.params.ArticleParam;
import idv.cheng.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cheng
 * @since 2021/12/4 12:23
 **/
@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ArticleBodyMapper articleBodyMapper;
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ThreadService threadService;

    @Override
    public Result listArticle(PageParams params) {
        Page<Article> page = new Page<>(params.getPage(), params.getPageSize());
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 是否置頂進行排序
        wrapper.orderByDesc(Article::getWeight);
        // order by create_date desc;
        wrapper.orderByDesc(Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, wrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> articleVos = copyList(records, true, true);
        return Result.success(articleVos);
    }

    @Override
    public Result getHotOrNewArticle(String type, int limit) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        switch (type) {
            case "hot":
                wrapper.orderByDesc(Article::getViewCounts);
                break;
            case "new":
                wrapper.orderByDesc(Article::getCreateDate);
            default:
                return Result.fail(-1, "type not found");
        }
        wrapper.select(Article::getId, Article::getTitle);
        wrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(wrapper);
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result listArticles() {
        List<Archives> archivesList = articleMapper.listArticles();
        return Result.success(archivesList);
    }

    @Override
    public Result findArticleById(Long articleId) {
        // 根據ID查詢文章
        // 根據bodyId & categoryId 做關連查詢
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true, true, true);
        // 查看文章後，新增閱讀數量
        // 查看文章後，應該直接返回資料，這時候做了一個更新，更新時加寫鎖就會阻塞其他的讀操作，性能就會低
        // 更新 增加了此次API的耗時，如果更新出問題，不能影響查看文章的操作

        // 使用執行緒池，解決上述問題
        // 把更新操作丟到池執行，就和主執行緒無關
        threadService.updateArticleViewCount(articleMapper, article);
        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParam param) {
        // 此API要加入登入攔截當中，否則會取不到User訊息
        SysUser sysUser = UserThreadLocal.get();
        // 發佈文章，目的建立Article
        // 作者ID，既當前的登入使用者
        // 要將標籤加入到關連列表當中
        // body內容儲存article bodyId

        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.ARTICLE_COMMON);
        article.setViewCounts(0);
        article.setTitle(param.getTitle());
        article.setSummary(param.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(param.getCategoryVo().getId());
        // insert後，會產生一個文章ID
        articleMapper.insert(article);
        // tag
        List<TagVo> tags = param.getTags();
        if (tags != null) {
            tags.forEach(t -> {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(t.getId());
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            });
        }

        // body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(param.getBody().getContent());
        articleBody.setContentHtml(param.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());
        return Result.success(map);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articles = new ArrayList<>();
        records.forEach(r -> articles.add(copy(r, isTag, isAuthor, false, false)));
        return articles;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articles = new ArrayList<>();
        records.forEach(r -> articles.add(copy(r, isTag, isAuthor, isBody, isCategory)));
        return articles;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
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
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
