package com.hamzasari.simplecrudresourceserver

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfiguration {
    @Bean
    fun filterChain(http: HttpSecurity):SecurityFilterChain {
        http.authorizeHttpRequests()
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer().jwt()
        return http.build()
    }

    @Value("\${auth0.audience}")
    private lateinit var audience: String

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-url}")
    private lateinit var issuer: String

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val jwtDecoder: NimbusJwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer)

        val audienceValidator: OAuth2TokenValidator<Jwt> = AudienceValidator(audience)

        val withIssuer: OAuth2TokenValidator<Jwt> = JwtValidators.createDefaultWithIssuer(issuer)

        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)

        jwtDecoder.setJwtValidator(withAudience)

        return jwtDecoder
    }
}