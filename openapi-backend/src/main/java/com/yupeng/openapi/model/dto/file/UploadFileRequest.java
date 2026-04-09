package com.yupeng.openapi.model.dto.file;

import java.io.Serializable;
import lombok.Data;

/**
 * File upload request
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@Data
public class UploadFileRequest implements Serializable {

    /**
     * Business
     */
    private String biz;

    private static final long serialVersionUID = 1L;
}