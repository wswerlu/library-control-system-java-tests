package org.library.domain.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.SneakyThrows;
import org.library.domain.error_model.DefaultErrorBody;
import org.library.domain.error_model.DefaultErrorResponse;
import org.library.exceptions.DefaultErrorException;
import org.springframework.http.HttpStatus;

public class CustomExceptionFilter implements Filter {

    @Override
    @SneakyThrows
    public Response filter(FilterableRequestSpecification filterableRequestSpecification,
                           FilterableResponseSpecification filterableResponseSpecification,
                           FilterContext filterContext) {

        Response response = filterContext.next(filterableRequestSpecification, filterableResponseSpecification);
        int statusCode = response.getStatusCode();

        if (statusCode != HttpStatus.OK.value() && statusCode != HttpStatus.CREATED.value()) {
            throw new DefaultErrorException(new DefaultErrorResponse()
                    .setBody(response.getBody().as(DefaultErrorBody.class))
                    .setStatusCode(response.getStatusCode()));
        }

        return response;
    }
}
