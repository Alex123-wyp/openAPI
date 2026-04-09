package com.yupeng.openapi.common;

import lombok.Data;

/**
 * Id Request
 * Encapuslate the primitive type of id to a class, which can be used in the request body of POST, PUT, DELETE methods.
 * @author yupeng
 */
@Data
public class IdRequest {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
