package ro.ubb.conference.web.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by langchristian96 on 6/2/2017.
 */

@Component
public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_OK, "Unauthorized");
    }

}
