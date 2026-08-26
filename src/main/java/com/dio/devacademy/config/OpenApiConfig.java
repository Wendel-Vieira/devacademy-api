package com.dio.devacademy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DevAcademy REST API")
                        .description("API RESTful para gerenciamento completo de desenvolvedores, planos de assinatura, carteira de XP/moedas, habilidades técnicas e certificações.")
                        .version("v1.0.0"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Ambiente Local (Desenvolvimento/H2)"),
                        new Server().url("https://devacademy-api-production.up.railway.app").description("Ambiente de Produção (Railway/PostgreSQL)")
                ));
    }
}