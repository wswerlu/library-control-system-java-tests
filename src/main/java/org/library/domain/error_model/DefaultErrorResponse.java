package org.library.domain.error_model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DefaultErrorResponse {

    private DefaultErrorBody body;
    private int statusCode;
}
