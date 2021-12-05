package idv.cheng.service;

import idv.cheng.vo.CategoryVo;

/**
 * @author cheng
 * @since 2021/12/5 16:04
 **/
public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);
}
