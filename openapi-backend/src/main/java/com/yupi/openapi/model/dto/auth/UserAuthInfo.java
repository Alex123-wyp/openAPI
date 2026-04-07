package com.yupi.openapi.model.dto.auth;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Minimal auth payload used by the gateway gRPC lookup.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthInfo implements Serializable {

    private Long userId;

    private String accessKey;

    private String secretKey;

    private boolean active;
}
