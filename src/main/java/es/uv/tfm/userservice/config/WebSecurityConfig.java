package es.uv.tfm.userservice.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import es.uv.tfm.userservice.filters.JwtFilter;
import es.uv.tfm.userservice.security.AuthEntryPointJwt;

@Configuration
@EnableWebSecurity
//@Profile(value = {"dev", "prod"})
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Autowired
	private UserDetailsService customUserDetailsService;

	@Autowired
	private JwtFilter jwtFilter;

	@Autowired
	DataSource dataSource;

//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		// configure AuthenticationManager so that it knows from where to load
//		// user for matching credentials
//		// Use BCryptPasswordEncoder
//		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//		
//		
//		
////		 auth.jdbcAuthentication().dataSource(dataSource).u
////         .usersByUsernameQuery(
////                 "SELECT username, password, enabled FROM users WHERE username=?")
////         .authoritiesByUsernameQuery(
////                 "SELECT username, 'ROLE_USER' FROM users WHERE username=?");
//		
//	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
				.authoritiesByUsernameQuery("SELECT username, 'ROLE_USER' FROM users WHERE username=?");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.csrf().disable().headers().and()
		.cors().and()
				
				.authorizeRequests()
				.antMatchers("/auth/authenticate", "/account").permitAll()
				.antMatchers("/resources/", "/webjars/", "/assets/").permitAll()
				.antMatchers(HttpMethod.POST, "/account/").permitAll()
				.antMatchers(HttpMethod.POST, "/auth/authenticate/").permitAll()
				.antMatchers(HttpMethod.GET, "/roles/").permitAll()
				.anyRequest().authenticated()
				.and()

				// make sure we use stateless session; session won't be used to
				// store user's state.
//						exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and();
		/*
		 * // http // .httpBasic().disable() // Desactiva la pantalla de login´ // //
		 * Cross Site Request Forgery - Se desactiva // .csrf().disable() ////
		 * .authorizeRequests().antMatchers("/resources/", "/webjars/",
		 * "/assets/").permitAll().antMatchers("/") //
		 * .authorizeRequests().antMatchers("/auth/authenticate").permitAll() //
		 * .antMatchers(HttpMethod.POST, "/account/").permitAll() // //
		 * .antMatchers("/roles/**").hasRole("ADMIN") ////
		 * .antMatchers("/account/").permitAll() // //
		 * .antMatchers("/roles/**").hasRole("ADMIN") // .anyRequest().authenticated()
		 * // .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.
		 * STATELESS) // Importante para que // // no cree sesiones, // // y pida // //
		 * el token para // // cada recurso // // protegido
		 */

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(customUserDetailsService);
//	}
//
//	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new CustomUserDetailsService();
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
	}

//
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//	}
//
	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		jdbcUserDetailsManager.setDataSource(dataSource);
		return jdbcUserDetailsManager;
	}

//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		http
//		.httpBasic().disable() // Desactiva la pantalla de login´
//				// Cross Site Request Forgery - Se desactiva 
//				.csrf().disable()
////				.authorizeRequests().antMatchers("/resources/", "/webjars/", "/assets/").permitAll().antMatchers("/")
//                .authorizeRequests().antMatchers("/auth/authenticate").permitAll()
//				.antMatchers(HttpMethod.POST, "/account/").permitAll()
//				// .antMatchers("/roles/**").hasRole("ADMIN")
////				.antMatchers("/account/").permitAll()
//				// .antMatchers("/roles/**").hasRole("ADMIN")
//				 .anyRequest().authenticated()
//				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Importante para que
//																									// no cree sesiones,
//																									// y pida
//																									// el token para
//																									// cada recurso
//																									// protegido
//
//			
//			                
//			   
//				
//				
//				
////				        http.authorizeRequests()
////				            .antMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
////				            .antMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
////				            .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
////				            .antMatchers("/delete/**").hasAuthority("ADMIN")
////				            .anyRequest().authenticated()
////				            .and()
//
////			.and();
////			.formLogin()
////				.loginPage("/login")
////				.defaultSuccessUrl("/home")
////				.failureUrl("/login?error")
////				.permitAll()
////				.and()
////			.logout()
////				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
////				.logoutSuccessUrl("/login?logout")
////				.permitAll()
////				.and()
////			.exceptionHandling()
////				.accessDeniedPage("/accessDenied");
//		;
//
//		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//	}
//
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

		source.registerCorsConfiguration("/**", configuration);

//		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;

//	    String localURI = "http://localhost:4200";
//	
//	    List<String> allowedOrigins = Arrays.asList(new String[]{localURI});
//
//	    allowedOrigins.add(localURI);
//	    final CorsConfiguration configuration = new CorsConfiguration();
//	    configuration.setAllowedOrigins(allowedOrigins);
//	    
//	    List<String> methods = Arrays.asList(new String[]{"HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"});
//
//	    
//	    configuration.setAllowedMethods(methods);
//	    // setAllowCredentials(true) is important, otherwise:
//	    // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
//	    configuration.setAllowCredentials(true);
//	    // setAllowedHeaders is important! Without it, OPTIONS preflight request
//	    // will fail with 403 Invalid CORS request
//	    
//	    List<String> headers = Arrays.asList(new String[]{"Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Origin"});
//
//	    configuration.setAllowedHeaders(headers);
//	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    source.registerCorsConfiguration("/**", configuration);
//	    return source;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("Authorization", "Cache-Control", "Content-Type");
			}
		};
	}

}
