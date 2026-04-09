package com.yupeng.openapi.common;

import java.io.Serializable;
import lombok.Data;

/**
 * Delete Request
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}