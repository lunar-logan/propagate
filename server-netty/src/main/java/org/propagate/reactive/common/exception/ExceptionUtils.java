package org.propagate.reactive.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ExceptionUtils {
    private ExceptionUtils() {
    }

    public static String toString(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        final PrintWriter pw = new PrintWriter(stringWriter);
        ex.printStackTrace(pw);
        return stringWriter.toString();
    }

    public static int getHttpStatusCode(Exception ex) {
        if (ex instanceof WebExchangeBindException) {
            return HttpStatus.BAD_REQUEST.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
