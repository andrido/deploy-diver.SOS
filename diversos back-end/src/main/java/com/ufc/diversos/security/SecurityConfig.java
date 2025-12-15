package com.ufc.diversos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable()) // 1. CRÍTICO: Desabilita CSRF para permitir o POST do CURL
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // --- ROTAS PÚBLICAS ---
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/vagas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/vagas/buscar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/vagas/{id}").permitAll()

                        .requestMatchers(HttpMethod.GET, "/noticias/**").permitAll()

                        // 2. LIBERAÇÃO TEMPORÁRIA: Permite POST /vagas sem autenticação
                        .requestMatchers(HttpMethod.POST, "/vagas/**").permitAll()

                        // --- ROTAS DO USUÁRIO LOGADO (MEU PERFIL) ---
                        .requestMatchers("/usuarios/me/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/usuarios/**").authenticated()

                        // --- ROTAS RESTRITAS (ADMIN/MOD) ---
                        // 3. CORREÇÃO PERMANENTE: Troca hasAnyRole por hasAnyAuthority em TODAS as restrições
                        // Isso usa o nome exato do perfil do banco (ADMINISTRADOR, MODERADOR)
                        .requestMatchers(HttpMethod.GET, "/usuarios").hasAnyAuthority("ADMINISTRADOR", "MODERADOR")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasAnyAuthority("ADMINISTRADOR", "MODERADOR")

                        // Rotas de Edição/Deleção de Vagas (Mantidas restritas)
                        .requestMatchers(HttpMethod.PUT, "/vagas/**").hasAnyAuthority("ADMINISTRADOR", "MODERADOR")
                        .requestMatchers(HttpMethod.DELETE, "/vagas/**").hasAnyAuthority("ADMINISTRADOR", "MODERADOR")

                        // Notícias restritas
                        .requestMatchers("/noticias/**").hasAnyAuthority("ADMINISTRADOR", "MODERADOR")

                        // --- RESTO ---
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(HttpMethod.GET, "/vagas/**")
                .requestMatchers(HttpMethod.GET, "/noticias/**")
                .requestMatchers("/auth/login", "/usuarios");
    }
}