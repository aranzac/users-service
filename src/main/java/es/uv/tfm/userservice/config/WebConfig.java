package es.uv.tfm.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
public class WebConfig implements WebMvcConfigurer {


	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/admin/home").setViewName("adminhome");
		registry.addViewController("/accessDenied").setViewName("403");
	}

	@Bean
	public SpringSecurityDialect securityDialect() {
		return new SpringSecurityDialect();
	}
	
	@Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry
        		.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("*").allowedHeaders("*")

//                .allowedOrigins("http://localhost:4200")
//                .allowedMethods("*")
//                .maxAge(3600L)
//                .allowedHeaders("*")
                //.exposedHeaders("Authorization")
                //.allowCredentials(true)
                ;
        corsRegistry.addMapping("/roles/**").allowedOrigins("http://localhost:4200").allowedMethods("*").allowedHeaders("*");
                
	}
}
