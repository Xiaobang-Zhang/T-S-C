/**
 * 
 */
/**
 * @author Maple
 *
 */
package org.maple.tsc.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.maple.tsc.constants.CommonConstants;
import org.maple.tsc.exception.TSCException;
import org.maple.tsc.models.UserModel;
import org.maple.tsc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	final String COOKIE_USER_ID = "userId";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
				.antMatchers("/global/**").permitAll()
				.antMatchers("/TSCinternal/AdminContents/**", "/admin").access("hasRole('" + CommonConstants.CODETABLE_NAME_ADMIN + "')")
				.antMatchers("/TSCinternal/ApplicantContents/**", "/user")
					.access("hasRole('" + CommonConstants.CODETABLE_NAME_TEACHER + "') or hasRole('" + CommonConstants.CODETABLE_NAME_STUDENT + "')")
				.antMatchers("/comment/**", "/course/**", "/topic/**", "/user/**", "/TSCinternal/**").authenticated()
				.and()
			.formLogin()
				.loginPage("/login").permitAll().successHandler(getAuthenticationSuccessHandler())
				.failureUrl("/login?error")
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessHandler(getLogoutSuccessHandler()).invalidateHttpSession(true).clearAuthentication(true).deleteCookies(COOKIE_USER_ID);
		http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false).expiredUrl("/login");
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		String usersByUsernameQueryString = 
				"SELECT CAST(u.account_id AS VARCHAR) username, auth.user_password \"password\", u.is_active enabled "
				+ "FROM \"authorization\" auth "
				+ "JOIN \"user\" u ON auth.user_id = u.id "
				+ "WHERE u.account_id = CAST(? AS bigint)";
		String authoritiesByUsernameQueryString = 
				"SELECT CAST(u.account_id AS VARCHAR) username, code.name \"role\" "
				+ "FROM \"user\" u "
				+ "JOIN codetable code ON u.user_role = code.id "
				+ "WHERE account_id = CAST(? AS bigint)";
		
		JdbcUserDetailsManager userDetailService = auth.jdbcAuthentication().dataSource(dataSource).getUserDetailsService();
		
		userDetailService.setUsersByUsernameQuery(usersByUsernameQueryString);
		userDetailService.setAuthoritiesByUsernameQuery(authoritiesByUsernameQueryString);
	}
	
	@Autowired
	UserService cusUserService;
	
	AuthenticationSuccessHandler getAuthenticationSuccessHandler(){
		return new AuthenticationSuccessHandler(){
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				
				String accountId = ((User)authentication.getPrincipal()).getUsername();
				UserModel user = null;
				try {
					user = cusUserService.selectByAccountId(Long.valueOf(accountId));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (TSCException e) {
					e.printStackTrace();
				}
				response.addCookie(new Cookie(COOKIE_USER_ID, user.getId().toString()));
				
				Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
				for(GrantedAuthority authority : authorities){
					if(CommonConstants.CODETABLE_NAME_ADMIN.equals(authority.getAuthority())){
						response.sendRedirect("/admin");
					}
					else if(CommonConstants.CODETABLE_NAME_TEACHER.equals(authority.getAuthority())){
						response.sendRedirect("/user");
					}
					else if(CommonConstants.CODETABLE_NAME_STUDENT.equals(authority.getAuthority())){
						response.sendRedirect("/user");
					}
				}
			}
			
		};
	}
	
	LogoutSuccessHandler getLogoutSuccessHandler(){
		return new LogoutSuccessHandler(){

			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				
				String accountId = ((User)authentication.getPrincipal()).getUsername();
				UserModel user = new UserModel();
				user.setAccountId(Long.valueOf(accountId));
				user.setLastLeftDt(new Date());
				try {
					cusUserService.updateByAccountIdSelective(user);
				} catch (TSCException e) {
					e.printStackTrace();
				}
				
				response.sendRedirect("/login?logout");
			}
			
		};
	}

}