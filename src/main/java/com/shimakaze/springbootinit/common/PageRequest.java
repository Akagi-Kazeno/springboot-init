package com.shimakaze.springbootinit.common;

import com.shimakaze.springbootinit.constant.SortConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequest {
    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序(默认升序)
     */
    private String sortOrder = SortConstant.SORT_ORDER_ASC;
}
