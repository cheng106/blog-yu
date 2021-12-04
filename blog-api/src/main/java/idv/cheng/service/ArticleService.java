package idv.cheng.service;

import idv.cheng.vo.Result;
import idv.cheng.vo.params.PageParams;

/**
 * @author cheng
 * @since 2021/12/4 12:21
 **/
public interface ArticleService {

    /**
     * 分頁查詢 文章列表
     *
     * @param page 分頁參數
     * @return idv.cheng.vo.Result
     **/
    Result listArticle(PageParams page);

    /**
     * 首頁熱門文章 or 最新文章
     *
     * @param limit 前N條
     * @return idv.cheng.vo.Result
     **/
    Result getHotOrNewArticle(String type, int limit);

    /**
     * 文章歸檔
     *
     * @return idv.cheng.vo.Result
     **/
    Result listArticles();
}
