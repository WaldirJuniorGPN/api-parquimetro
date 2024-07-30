package br.com.fiap.api_parquimetro.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiBuilderCustomizer;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenApiCustomizer customOpenApi(){
        return new OpenApiCustomizer() {
            @Override
            public void customise(OpenAPI openApi) {
                openApi.setInfo(new Info().title("Parquímetro API").description("API para gerenciamento de parquímetros").version("1.0.0").summary("API para gerenciamento de parquímetros"));
            }
        };
    }


}
