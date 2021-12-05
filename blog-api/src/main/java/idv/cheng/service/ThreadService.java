package idv.cheng.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import idv.cheng.dao.mapper.ArticleMapper;
import idv.cheng.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 * @since 2021/12/5 16:28
 **/
@Component
public class ThreadService {

    // 期望此操作在執行緒池執行，不影響原有的主執行緒
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(++viewCounts);
        LambdaUpdateWrapper<Article> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Article::getId, article.getId());
        // 設定一個為了在多執行緒環境下，執行緒安全
        wrapper.eq(Article::getViewCounts, viewCounts);
        // update article set view_count = 100 where view_count = 99 and id = 11
        articleMapper.update(articleUpdate, wrapper);
    }
}
