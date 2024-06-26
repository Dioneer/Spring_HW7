package Pegas.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static Pegas.entity.Role.admin;
import static Pegas.entity.Role.user;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SuccessHandler successHandler;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/users/registration", "/login").permitAll()
                        .requestMatchers(antMatcher("/users/{\\d}")).hasAnyAuthority(user.getAuthority(),admin.getAuthority())
                        .requestMatchers(antMatcher( "/api/v1/users/{\\d}/avatar")).hasAnyAuthority(user.getAuthority(),admin.getAuthority())
                        .requestMatchers("/public-data", "/users").hasAnyAuthority(user.getAuthority(),admin.getAuthority())

                        .requestMatchers(antMatcher("/users/{\\d}/**")).hasAuthority(admin.getAuthority())
                        .requestMatchers("/users/{id}/**","/private-data").hasAuthority(admin.getAuthority())
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(successHandler)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
