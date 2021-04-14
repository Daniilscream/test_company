package ru.aisa.test_company.restservice;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = { "io.swagger", "ru.aisa.test_company.swagger.api",
		"io.swagger.configuration", "ru.aisa.test_company.restservice.controller",
		"ru.aisa.test_company.restservice.admin"})
public class Application implements CommandLineRunner {

	@Override
	public void run(String... arg0) throws Exception {
		if (arg0.length > 0 && arg0[0].equals("exitcode")) {
			throw new ExitException();
		}
	}

	public static void main(String[] args) throws Exception {
		new SpringApplication(Application.class).run(args);
		IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
		SecurityManager securityManager = new DefaultSecurityManager(iniRealm);

		SecurityUtils.setSecurityManager(securityManager);
		Subject currentUser = SecurityUtils.getSubject();
	}

	class ExitException extends RuntimeException implements ExitCodeGenerator {
		private static final long serialVersionUID = 1L;

		@Override
		public int getExitCode() {
			return 10;
		}
	}

//	@Bean
//	public Realm realm() {
//		return new MyCustomRealm();
//	}

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition filter
				= new DefaultShiroFilterChainDefinition();

		filter.addPathDefinition("/secure", "authc");
		filter.addPathDefinition("/**", "anon");

		return filter;
	}
}
