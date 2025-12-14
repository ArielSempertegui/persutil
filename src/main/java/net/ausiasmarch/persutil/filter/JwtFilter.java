package net.ausiasmarch.persutil.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.ausiasmarch.persutil.helper.JWTHelper;

@Component
public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
        } else {
            // aqui el código de una consulta que no es preflight
            String authToken = ((HttpServletRequest) request).getHeader("Authorization");
            if (authToken != null && authToken.startsWith("Bearer ")) {
                authToken = authToken.substring(7);
                // debug
                System.out.println("Method: " + ((HttpServletRequest) request).getMethod());
                System.out.println("URL: " + ((HttpServletRequest) request).getRequestURL().toString());
                System.out.println("Auth Token: " + authToken);
            } else {
                System.out.println("Method: " + ((HttpServletRequest) request).getMethod());
                System.out.println("URL: " + ((HttpServletRequest) request).getRequestURL().toString());
                System.out.println("Auth Token: No se ha proporcionado");
                ((HttpServletRequest) request).setAttribute("username", null);
                chain.doFilter(request, response);
            }
            // Validar el token JWT
            try {
                // Aquí va la lógica para validar el token JWT
                // Si el token es válido, continuar con la cadena de filtros
                String username = JWTHelper.validate(authToken);
                if (username != null) {
                    // Si el token es válido, continuar con la cadena de filtros
                    ((HttpServletRequest) request).setAttribute("username", username);
                    chain.doFilter(request, response);
                } else {
                    ((HttpServletRequest) request).setAttribute("username", null);
                    // Si el token no es válido, devolver un error 401
                    ((HttpServletResponse) response)
                            .setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } catch (Exception e) {
                ((HttpServletRequest) request).setAttribute("username", null);
                // Si el token no es válido, devolver un error 401
                ((HttpServletResponse) response)
                        .setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
    }

}
