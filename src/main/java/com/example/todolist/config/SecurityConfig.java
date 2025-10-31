package com.example.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Configurazione super basica di Spring Security.
     * - Permettiamo l'accesso senza login solo al CSS e alla pagina di login.
     * - Tutto il resto richiede autenticazione con form personalizzato.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Definiamo quali URL sono pubblici e quali no.
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/styles.css", "/h2-console/**", "/login", "/login/**").permitAll()
                .anyRequest().authenticated()
            )
            // Attiviamo il login tramite semplice form HTML.
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            // Consigliamo un logout semplice che torna alla pagina di login.
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            // H2 console richiede disabilitare CSRF e frame protection su quell'URL.
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );
        return http.build();
    }

    /**
     * Utente in-memory: username "utente", password "password".
     * Perfetto per demo o progetti super semplici.
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        var user = User.builder()
            .username("utente")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Usiamo il delegating encoder per non pensare agli algoritmi a mano.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
