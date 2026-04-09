package com.yupeng.openapi.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL utilities
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
public class SqlUtils {

    /**
     * Validate whether the sort field is legal (prevent SQL injection).
     *
     * @param sortField
     * @return
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}
