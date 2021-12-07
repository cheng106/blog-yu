package idv.cheng.controller;

import idv.cheng.aop.LogApi;
import idv.cheng.service.ArticleService;
import idv.cheng.vo.Result;
import idv.cheng.vo.params.ArticleParam;
import idv.cheng.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cheng
 * @since 2021/12/4 12:14
 **/
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首頁 文章列表
     *
     * @param page 分頁參數
     * @return idv.cheng.vo.Result
     **/
    @PostMapping
    @LogApi(module = "文章", operator = "取得文章列表")
    public Result listArticle(@RequestBody PageParams page) {
        return articleService.listArticle(page);
    }

    /**
     * 首頁熱門文章(hot) or 最新文章(new)
     *
     * @return idv.cheng.vo.Result
     **/
    @PostMapping("/{type}")
    public Result hotArticle(@PathVariable String type) {
        int limit = 5;
        return articleService.getHotOrNewArticle(type, limit);
    }

    @PostMapping("/listArchives")
    public Result listArchives() {
        return articleService.listArticles();
    }

    @PostMapping("/article/view/{articleId}")
    public Result findArticleById(@PathVariable Long articleId) {
        return articleService.findArticleById(articleId);
    }

    @PostMapping("/article/publish")
    public Result publish(@RequestBody ArticleParam param) {
        return articleService.publish(param);
    }

}
