package idv.cheng.vo.params;

import lombok.Data;

/**
 * @author cheng
 * @since 2021/12/4 12:16
 **/
@Data
public class PageParams {
    private int page = 1;
    private int pageSize = 10;
}
