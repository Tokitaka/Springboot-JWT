package shop.mtcoding.jwtclone.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import shop.mtcoding.jwtclone.config.auth.JwtProvider;
import shop.mtcoding.jwtclone.config.auth.LoginUser;

public class JwtVerifyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String prefixJwt = req.getHeader(JwtProvider.HEADER);
        String jwt = prefixJwt.replace(JwtProvider.TOKEN_PREFIX, "");
        // jwt decode
        try {
            DecodedJWT decodedJWT = JwtProvider.verify(jwt);
            // try-catch (SignatureVerificationException, TokenExpiredException)
            int id = decodedJWT.getClaim("id").asInt();
            String role = decodedJWT.getClaim("role").asString();
            HttpSession session = req.getSession();
            LoginUser loginUser = LoginUser.builder().id(id).role(role).build();
            session.setAttribute("loginUser", loginUser);
            chain.doFilter(req, resp); // to next filter
        } catch (SignatureVerificationException sve) {
            resp.setStatus(401); // Unauthorized
            resp.setContentType("text/plain; charset=utf-8");
            resp.getWriter().println("Unauthorized");
        } catch (TokenExpiredException tee) {
            resp.setStatus(401); // Unauthorized
            resp.setContentType("text/plain; charset=utf-8");
            resp.getWriter().println("Please login again");
        }

    }

}
