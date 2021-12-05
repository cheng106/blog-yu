package idv.cheng.controller;

import idv.cheng.service.CommentService;
import idv.cheng.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cheng
 * @since 2021/12/5 16:56
 **/
@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/article/{id}")
    public Result comments(@PathVariable Long id) {
        return commentService.commentsByArticleId(id);
    }
}
