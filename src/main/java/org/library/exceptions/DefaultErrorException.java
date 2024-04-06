package org.library.exceptions;

import org.library.domain.error_model.DefaultErrorResponse;

public class DefaultErrorException extends Exception {

    public DefaultErrorException(DefaultErrorResponse response) {
        super(response.getBody().getErrorMessage());
    }
}
