package br.com.omarcovelho.microservices.composite.product;

import br.com.omarcovelho.microservices.composite.product.config.SwaggerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@SpringBootApplication
@ComponentScan("br.com.omarcovelho")
@RequiredArgsConstructor
public class ProductCompositeServiceApplication {

	private final SwaggerProperties swaggerProperties;

	public static void main(String[] args) {
		SpringApplication.run(ProductCompositeServiceApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Docket apiDocumentation() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(basePackage("br.com.omarcovelho"))
				.paths(PathSelectors.any())
				.build()
				.globalResponses(HttpMethod.GET, Collections.emptyList())
				.apiInfo(new ApiInfo(
						swaggerProperties.getTitle(),
						swaggerProperties.getDescription(),
						swaggerProperties.getVersion(),
						swaggerProperties.getTermsOfServiceUrl(),
						new Contact(
								swaggerProperties.getContactName(),
								swaggerProperties.getContactUrl(),
								swaggerProperties.getContactEmail()
						),
						swaggerProperties.getLicense(),
						swaggerProperties.getLicenseUrl(),
						Collections.emptyList()
				));
	}
}
