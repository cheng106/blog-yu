package idv.cheng.service;

import idv.cheng.vo.Result;
import idv.cheng.vo.params.CommentParam;

/**
 * @author cheng
 * @since 2021/12/5 16:58
 **/
public interface CommentService {

    /**
     * 根據文章ID，查所有評論列表
     *
     * @param id 文章ID
     * @return Result
     **/
    Result commentsByArticleId(Long id);

    Result comment(CommentParam param);
}
