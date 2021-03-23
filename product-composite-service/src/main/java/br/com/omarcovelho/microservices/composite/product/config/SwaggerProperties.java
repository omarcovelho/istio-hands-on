/*
 * @(#)SwaggerProperties.java 1.0 22/03/2021
 *
 * Copyright (c) 2021, Embraer. All rights reserved. Embraer S/A
 * proprietary/confidential. Use is subject to license terms.
 */
package br.com.omarcovelho.microservices.composite.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author marprado - Marco Prado
 * @version 1.0 22/03/2021
 */
@Configuration
@ConfigurationProperties(prefix = "api.common")
@Data
public class SwaggerProperties {

    private String version;
    private String title;
    private String description;
    private String termsOfServiceUrl;
    private String license;
    private String licenseUrl;
    private String contactName;
    private String contactUrl;
    private String contactEmail;
}
