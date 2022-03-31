package org.sang.config;

import org.sang.authentications.fliters.MobilePhoneAuthenticationFilter;
import org.sang.authentications.handlers.AuthenticationAccessDeniedHandler;
import org.sang.authentications.handlers.FormLoginFailedHandler;
import org.sang.authentications.handlers.FormLoginSuccessHandler;
import org.sang.authentications.providers.MobilePhoneAuthenticationProvider;
import org.sang.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
/**
 * Created by sang on 2017/12/17.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserService userService;
    @Resource
    RedisTemplate redisTemplate;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        MobilePhoneAuthenticationProvider mobilePhoneAuthenticationProvider = this.mobilePhoneAuthenticationProvider();
        auth.authenticationProvider(mobilePhoneAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 添加手机登录认证过滤器,在构造函数中设置拦截认证请求路径
        MobilePhoneAuthenticationFilter mobilePhoneAuthenticationFilter = new MobilePhoneAuthenticationFilter("/mobile/login");
        mobilePhoneAuthenticationFilter.setAuthenticationSuccessHandler(new FormLoginSuccessHandler());
        mobilePhoneAuthenticationFilter.setAuthenticationFailureHandler(new FormLoginFailedHandler());
        // 下面这个authenticationManager必须设置，否则在MobilePhoneAuthenticationFilter#attemptAuthentication
        // 方法中调用this.getAuthenticationManager().authenticate(authRequest)方法时会报NullPointException
        mobilePhoneAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        mobilePhoneAuthenticationFilter.setAllowSessionCreation(true);
        http.addFilterAfter(mobilePhoneAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // 配置跨域
        http.cors().configurationSource(corsConfigurationSource());
        // 禁用spring security框架的退出登录，使用自定义退出登录
        http.logout().disable();
        http.authorizeRequests()
                .antMatchers("/user/reg").anonymous()
                .antMatchers("/sendLoginVerifyCode").anonymous()
                .antMatchers("/doc.html").hasAnyRole("user", "admin")
                .antMatchers("/admin/**").hasRole("admin")
                ///admin/**的URL都需要有超级管理员角色，如果使用.hasAuthority()方法来配置，需要在参数中加上ROLE_,如下.hasAuthority("ROLE_超级管理员")
                .anyRequest().authenticated()//其他的路径都是登录后即可访问
                .and().formLogin().loginPage("http://localhost:3000/#/login")
                .successHandler(new FormLoginSuccessHandler())
                .failureHandler(new FormLoginFailedHandler()).loginProcessingUrl("/user/login")
                .usernameParameter("username").passwordParameter("password").permitAll()
                .and().logout().permitAll().and().csrf().disable().exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/blogimg/**","/index.html","/static/**");
    }

    @Bean
    AccessDeniedHandler getAccessDeniedHandler() {
        return new AuthenticationAccessDeniedHandler();
    }

    //配置跨域访问资源
    private CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");	//同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedMethod("*");	//允许的请求方法，PSOT、GET等
        corsConfiguration.setAllowCredentials(true);
        // 注册跨域配置
        source.registerCorsConfiguration("/**",corsConfiguration); //配置允许跨域访问的url
        return source;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public MobilePhoneAuthenticationProvider mobilePhoneAuthenticationProvider() {
        MobilePhoneAuthenticationProvider mobilePhoneAuthenticationProvider = new MobilePhoneAuthenticationProvider(userService, redisTemplate);
        return mobilePhoneAuthenticationProvider;
    }
}
