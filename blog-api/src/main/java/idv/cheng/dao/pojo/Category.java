package idv.cheng.dao.pojo;

import lombok.Data;

/**
 * @author cheng
 * @since 2021/12/5 15:49
 **/
@Data
public class Category {
    private Long id;
    private String avatar;
    private String categoryName;
    private String description;
}
