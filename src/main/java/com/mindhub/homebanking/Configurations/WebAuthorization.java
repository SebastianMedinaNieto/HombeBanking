package com.mindhub.homebanking.Configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()


                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers("/web/index.html","/web/style/indexStyle.css","/web/script/indexJs.js","/web/style/gateto.jpg").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PATCH, "/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers( HttpMethod.PATCH, "/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/transaction").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/loan").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/clients/current/cardTransaction").hasAuthority("CLIENT")
                .antMatchers("/api/loans").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/clients/current/accounts/**").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current").hasAuthority("CLIENT")
                .antMatchers("/web/**").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.POST, "/api/admin/loan").hasAuthority("ADMIN")
                .antMatchers("/web/loanCreation.html", "/web/loanCreation.js").hasAuthority("ADMIN")
                .antMatchers("/api/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console").hasAnyAuthority("ADMIN");


        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");



        http.logout().logoutUrl("/api/logout");


        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }
}