package idv.cheng.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import idv.cheng.dao.mapper.TagMapper;
import idv.cheng.dao.pojo.Tag;
import idv.cheng.vo.Result;
import idv.cheng.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author cheng
 * @since 2021/12/4 13:03
 **/
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        // mybatis-plus無法進行多表查詢
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result hots(int limit) {
        // 1.標籤所擁有的文章數量最多
        // 2.查詢根據tagId分組 計算數量
        // select tag_id from yu_article_tag group by tag_id order by count(*) desc limit 2;
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        if (tagIds.isEmpty()) {
            return Result.success(Collections.emptyList());
        }
        // 需要的是tagId和tagName
        // select * from tag where id in (1,2,3,4)
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return Result.success(tagList);
    }

    @Override
    public Result findAll() {
        List<Tag> tags = tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tags));
    }

    public TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

    public List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        tagList.forEach(t -> tagVoList.add(copy(t)));
        return tagVoList;
    }
}
