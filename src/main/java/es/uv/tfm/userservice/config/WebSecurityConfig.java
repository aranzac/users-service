package es.uv.tfm.userservice.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
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

import es.uv.tfm.userservice.filters.JwtFilter;
import es.uv.tfm.userservice.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
//@Profile(value = {"dev", "prod"})
@EnableGlobalMethodSecurity(securedEnabled= true, prePostEnabled = true, jsr250Enabled = true )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// @Autowired
	// private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private UserDetailsService customUserDetailsService;

	@Autowired
	private JwtFilter jwtFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService);
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

//	@Autowired
//	private AuthEntryPointJwt unauthorizedHandler;
//	
//	
//	@Bean
//	public AuthTokenFilter authenticationJwtTokenFilter() {
//		return new AuthTokenFilter();
//	}

	@Autowired
	DataSource dataSource;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService)
		.passwordEncoder(passwordEncoder());
	}

	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		jdbcUserDetailsManager.setDataSource(dataSource);
		return jdbcUserDetailsManager;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().disable() // Desactiva la pantalla de login´
				// Cross Site Request Forgery - Se desactiva 
				.csrf().disable()
				.authorizeRequests().antMatchers("/resources/", "/webjars/", "/assets/").permitAll().antMatchers("/")
				.permitAll().antMatchers("/auth/authenticate").permitAll()
				//.antMatchers(HttpMethod.POST, "/account/").permitAll()
				// .antMatchers("/roles/**").hasRole("ADMIN")
//				.antMatchers("/account/").permitAll()
				// .antMatchers("/roles/**").hasRole("ADMIN")
				// .anyRequest().authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Importante para que
																									// no cree sesiones,
																									// y pida
																									// el token para
																									// cada recurso
																									// protegido

//				        http.authorizeRequests()
//				            .antMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
//				            .antMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
//				            .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
//				            .antMatchers("/delete/**").hasAuthority("ADMIN")
//				            .anyRequest().authenticated()
//				            .and()

//			.and();
//			.formLogin()
//				.loginPage("/login")
//				.defaultSuccessUrl("/home")
//				.failureUrl("/login?error")
//				.permitAll()
//				.and()
//			.logout()
//				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//				.logoutSuccessUrl("/login?logout")
//				.permitAll()
//				.and()
//			.exceptionHandling()
//				.accessDeniedPage("/accessDenied");
		;

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

//	@Override
//	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//	}

//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//	
//	

}
