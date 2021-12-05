package idv.cheng.controller;

import idv.cheng.service.CategoryService;
import idv.cheng.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cheng
 * @since 2021/12/5 18:24
 **/
@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result categoryList() {
        return categoryService.findAll();
    }
}
