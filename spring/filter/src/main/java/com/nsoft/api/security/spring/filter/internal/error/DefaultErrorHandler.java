package com.nsoft.api.security.spring.filter.internal.error;

import com.nsoft.api.security.spring.filter.error.ErrorHandler;
import com.nsoft.chiwava.core.commons.json.JsonMapper;
import com.nsoft.chiwava.core.error.AbstractError;
import com.nsoft.chiwava.core.error.Title;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultErrorHandler implements ErrorHandler {

    @Override
    public void handleInvalidBearerTokenError(HttpServletResponse response) {
        throwUnauthorizedError(response);
    }

    @Override
    public void handleJWTProcessingError(HttpServletResponse response) {
        throwUnauthorizedError(response);
    }

    private void throwUnauthorizedError(HttpServletResponse response) {
        UnauthorizedError error = new UnauthorizedError();

        ThrowableProblem problem = Problem.builder().withTitle(error.getTitle().toString())
                .withStatus(error.getStatus()).withDetail(error.getMessage())
                .build();

        JsonMapper jsonMapper = new JsonMapper.Builder().withModule(new ProblemModule()).build();

        response.setStatus(error.getStatus().getStatusCode());
        try {
            response.getWriter().write(jsonMapper.toJson(problem));
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class UnauthorizedError extends AbstractError {

        @Override
        public Status getStatus() {
            return Status.UNAUTHORIZED;
        }

        @Override
        public Title getTitle() {
            return new Title() {
                @Override
                public String toString() {
                    return "exception.invalid_token";
                }
            };
        }
    }
}
