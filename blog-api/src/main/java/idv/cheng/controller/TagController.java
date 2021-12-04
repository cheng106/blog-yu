package idv.cheng.controller;

import idv.cheng.service.TagService;
import idv.cheng.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cheng
 * @since 2021/12/4 16:48
 **/
@RestController
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagService tagService;

    // tags/hot
    @GetMapping("hot")
    public Result hot() {
        int limit = 6;
        return tagService.hots(limit);
    }
}
