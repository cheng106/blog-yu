package idv.cheng.controller;

import idv.cheng.service.ArticleService;
import idv.cheng.vo.Result;
import idv.cheng.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result listArticle(@RequestBody PageParams page) {
        return articleService.listArticle(page);
    }

    /**
     * 首頁熱門文章
     *
     * @return idv.cheng.vo.Result
     **/
    @PostMapping("/hot")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }
}
