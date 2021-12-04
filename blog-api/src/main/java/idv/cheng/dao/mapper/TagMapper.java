package idv.cheng.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import idv.cheng.dao.pojo.Tag;

import java.util.List;

/**
 * @author cheng
 * @since 2021/12/4 12:12
 **/
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根據文章ID查詢標籤列表
     *
     * @param articleId 文章ID
     * @return java.util.List<idv.cheng.dao.pojo.Tag>
     **/
    List<Tag> findTagsByArticleId(Long articleId);
}
