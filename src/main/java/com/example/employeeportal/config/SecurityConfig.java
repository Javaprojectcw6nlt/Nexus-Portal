package com.example.employeeportal.config;
import com.example.employeeportal.repository.AppUserRepository;
import org.springframework.context.annotation.*; import org.springframework.security.config.Customizer; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.core.userdetails.*; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.SecurityFilterChain;
@Configuration public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean UserDetailsService userDetailsService(AppUserRepository users){return username -> users.findByUsername(username).map(u -> User.withUsername(u.getUsername()).password(u.getPassword()).roles(u.getRole().replace("ROLE_","")).build()).orElseThrow(() -> new UsernameNotFoundException("User not found"));}
 @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  http.authorizeHttpRequests(a -> a.requestMatchers("/","/database","/chart","/contact","/login","/register","/css/**","/js/**","/h2-console/**","/api/chart/**").permitAll().requestMatchers(org.springframework.http.HttpMethod.GET,"/api/employees","/api/employees/**").permitAll().requestMatchers("/api/employees/**").hasRole("ADMIN").requestMatchers("/messages/**").authenticated().requestMatchers("/crud/**").hasRole("ADMIN").anyRequest().permitAll())
  .formLogin(f -> f.loginPage("/login").defaultSuccessUrl("/",true).permitAll()).httpBasic(Customizer.withDefaults()).logout(l -> l.logoutSuccessUrl("/?logout").permitAll())
  .csrf(c -> c.ignoringRequestMatchers("/h2-console/**", "/api/**")).headers(h -> h.frameOptions(f -> f.sameOrigin())); return http.build();
 }
}
