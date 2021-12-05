package idv.cheng.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import idv.cheng.dao.mapper.CommentMapper;
import idv.cheng.dao.pojo.Comment;
import idv.cheng.vo.CommentVo;
import idv.cheng.vo.Result;
import idv.cheng.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cheng
 * @since 2021/12/5 16:58
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService userService;

    @Override
    public Result commentsByArticleId(Long id) {
        // 根據文章ID查詢評論列表，從comment表查詢
        // 根據作者的ID查詢作者訊息
        // 判斷如果level = 1 要去查詢有沒有子評論
        // 如果有，根據評論ID查詢 (parent_id)

        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, id);
        wrapper.eq(Comment::getLevel, 1);
        List<Comment> comments = commentMapper.selectList(wrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        comments.forEach(c -> commentVoList.add(copy(c)));
        return commentVoList;
    }

    private CommentVo copy(Comment c) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(c, commentVo);
        // 作者訊息
        Long authorId = c.getAuthorId();
        UserVo userVo = userService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        // 子評論
        Integer level = c.getLevel();
        if (level == 1) {
            Long id = c.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrenList(commentVoList);
        }
        // to User給誰評論
        if (level > 1) {
            Long toUid = c.getToUid();
            UserVo toUserVo = userService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getParentId, id);
        wrapper.eq(Comment::getLevel, 2);
        return copyList(commentMapper.selectList(wrapper));
    }
}
