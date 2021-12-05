package idv.cheng.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import idv.cheng.dao.mapper.CategoryMapper;
import idv.cheng.dao.pojo.Category;
import idv.cheng.vo.CategoryVo;
import idv.cheng.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cheng
 * @since 2021/12/5 16:04
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    @Override
    public Result findAll() {
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(categoryList));
    }

    private CategoryVo copy(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    private List<CategoryVo> copyList(List<Category> categoryList) {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        categoryList.forEach(category -> categoryVoList.add(copy(category)));
        return categoryVoList;
    }
}
