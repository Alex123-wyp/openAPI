package com.yupeng.openapi.common;

import com.yupeng.openapi.constant.CommonConstant;
import lombok.Data;

/**
 * Page request
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@Data
public class PageRequest {

    /**
     * Current page number
     */
    private int current = 1;

    /**
     * Page size
     */
    private int pageSize = 10;

    /**
     * Sort field
     */
    private String sortField;

    /**
     * Sort order (default ascending)
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
