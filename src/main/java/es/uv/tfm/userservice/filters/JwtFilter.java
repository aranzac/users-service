package es.uv.tfm.userservice.filters;

import java.io.IOException;
import java.net.URL;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import es.uv.tfm.userservice.security.CustomUserDetailsService;
import es.uv.tfm.userservice.security.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService service;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

		String authorizationHeader = httpServletRequest.getHeader("Authorization");

		String token = null;
		String userName = null;

		System.out.println("############################## JWTFILTER ##############################");
		System.out.println("METHOD: " + httpServletRequest.getMethod());
		System.out.println("ORIGIN': " + httpServletRequest.getRemoteHost());
		System.out.println("ORIGIN': " + httpServletRequest.getRemotePort());
		System.out.println("URI: " + httpServletRequest.getRequestURI());

		System.out.println("AUTH:  " + httpServletRequest.getHeader("Authorization"));
		System.out.println("CONTENT-TYPE: " + httpServletRequest.getHeader("Content-Type"));
		System.out.println("HEADERS: " + httpServletRequest.getHeaderNames());
		System.out.println("HEADERS': " + httpServletRequest.getHeader("Access-Control-Request-Method"));
		System.out.println("HEADERS': " + httpServletRequest.getHeader("Access-Control-Request-Headers"));
		

		System.out.println("#######################################################################");

		String domain = new URL(httpServletRequest.getRequestURL().toString()).getHost(); 

		httpServletResponse.setHeader("Access-Control-Allow-Origin","http://localhost:4200");
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		httpServletResponse.setHeader("Access-Control-Max-Age", "86401");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpServletResponse.setStatus(200);
		System.out.println(httpServletResponse.getStatus());
		
//System.out.println(SC_OK);
		// Entra cuando se adjunta un auth bearer
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			token = authorizationHeader.substring(7);
			userName = jwtUtil.extractUsername(token);

		}

		// Entra la primera vez que valida
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = service.loadUserByUsername(userName);

			if (jwtUtil.validateToken(token, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}
