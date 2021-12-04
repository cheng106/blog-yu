package idv.cheng.service;

import idv.cheng.vo.TagVo;

import java.util.List;

/**
 * @author cheng
 * @since 2021/12/4 13:02
 **/
public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);
}
