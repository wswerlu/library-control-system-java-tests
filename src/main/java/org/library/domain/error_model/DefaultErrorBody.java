package org.library.domain.error_model;

import lombok.Data;

@Data
public class DefaultErrorBody {

    private String errorCode;
    private String errorMessage;
    private String errorDetails;
}
