package com.yupeng.openapi_gateway;

import jakarta.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

@SpringBootApplication
public class OpenapiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenapiGatewayApplication.class, args);
	}

	@Resource
	private NameApiAuthGatewayFilterFactory nameApiAuthGatewayFilterFactory;

	/**
	 * WebFlux style Spring Cloud Gateway demo routes.
	 */
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()

					.route("openapi-public-interface", r -> r
							.path("/api/name/**")
							.filters(f -> f
									.addResponseHeader("X-Gateway-Route", "openapi-public-interface")
									.dedupeResponseHeader("Access-Control-Allow-Origin Access-Control-Allow-Credentials Access-Control-Expose-Headers", "RETAIN_FIRST")
									.filter(nameApiAuthGatewayFilterFactory.apply(new NameApiAuthGatewayFilterFactory.Config())))
							.uri("http://127.0.0.1:8123"))
					// Backend APIs used by the frontend, for example: /api/user/**, /api/interfaceInfo/**
					.route("openapi-backend", r -> r
							.path("/api/**")
							.filters(f -> f
									.addResponseHeader("X-Gateway-Route", "openapi-backend")
									.dedupeResponseHeader("Access-Control-Allow-Origin Access-Control-Allow-Credentials Access-Control-Expose-Headers", "RETAIN_FIRST"))
							.uri("http://127.0.0.1:8101"))

				.build();
	}




	@Bean
	public CorsWebFilter corsWebFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOriginPatterns(Arrays.asList("http://localhost:*"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addExposedHeader("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsWebFilter(source);
	}


}
