package autorota;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .requiresChannel().anyRequest().requiresSecure()
                // Authentication
                .and().formLogin()
                // Show login page and use email as username
                .loginPage("/login")
                .usernameParameter("email")
                //Process authentication: /login handler method implemented by Spring Security
                .loginProcessingUrl("/login")
                // Handler for succesful login
                .defaultSuccessUrl("/home", true) // the second parameter is for enforcing this url always
                // Handler for failed login
                .failureUrl("/error-login")
                // everyone can access these requests
                .permitAll()
                .and()
                .logout()
                // to logout
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll()

                // Authorization
                .and()
                .authorizeRequests()
                .regexMatchers("\\/rota\\/\\?weekNum=[0-9]{1,2}&year=[0-9]{4}&create.*").hasRole("ADMIN")
                .regexMatchers("\\/rota\\/\\?weekNum=[0-9]{1,2}&year=[0-9]{4}&edit.*").hasRole("ADMIN")
                .antMatchers("/rota/add").hasRole("ADMIN")
                .antMatchers("/rota/edited").hasRole("ADMIN")
                .antMatchers("/register/**").hasRole("ADMIN")
                .antMatchers("/holiday/all").hasRole("ADMIN")
                .antMatchers("/holiday/approved").hasRole("ADMIN")
                .antMatchers("/booking/**").hasRole("ADMIN")

                .antMatchers("/booking/new").hasRole("ADMIN")
                .antMatchers("/settings/**").hasRole("ADMIN")

                .anyRequest().authenticated() // all requests ABOVE this statement require authentication
                .and()
                // to redirect the user when trying to access a resource to which access is not granted
                .exceptionHandling().accessDeniedPage("/access-denied");
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(pe);

    }

}
	