/*
 * @(#)ProductCompositeIntegration.java 1.0 22/03/2021
 *
 * Copyright (c) 2021, Embraer. All rights reserved. Embraer S/A
 * proprietary/confidential. Use is subject to license terms.
 */
package br.com.omarcovelho.microservices.composite.product.services;

import br.com.omarcovelho.api.composite.core.product.Product;
import br.com.omarcovelho.api.composite.core.product.ProductService;
import br.com.omarcovelho.api.composite.core.recommendation.Recommendation;
import br.com.omarcovelho.api.composite.core.recommendation.RecommendationService;
import br.com.omarcovelho.api.composite.core.review.Review;
import br.com.omarcovelho.api.composite.core.review.ReviewService;
import br.com.omarcovelho.util.exceptions.InvalidInputException;
import br.com.omarcovelho.util.exceptions.NotFoundException;
import br.com.omarcovelho.util.http.HttpErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author marprado - Marco Prado
 * @version 1.0 22/03/2021
 */
@Component
@Slf4j
public class ProductCompositeIntegration implements ProductService, RecommendationService, ReviewService {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;

    public ProductCompositeIntegration(
            RestTemplate restTemplate,
            ObjectMapper mapper,
            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.product-service.port}") int productServicePort,
            @Value("${app.recommendation-service.host}") String
                    recommendationServiceHost,
            @Value("${app.recommendation-service.port}") int
                    recommendationServicePort,
            @Value("${app.review-service.host}") String reviewServiceHost,
            @Value("${app.review-service.port}") int reviewServicePort
    ){
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/product/";
        recommendationServiceUrl = "http://" + recommendationServiceHost + ":" + recommendationServicePort + "/recommendation?productId=";
        reviewServiceUrl = "http://" + reviewServiceHost +":" + reviewServicePort + "/review?productId=";
    }

    @Override
    public Product getProduct(final int productId) {
        try {
            String url = productServiceUrl + productId;
            log.debug("Will call getProduct API on URL: {}", url);

            Product product = restTemplate.getForObject(url, Product.class);
            log.debug("Found a product with id: {}", product.getProductId());

            return product;

        } catch (HttpClientErrorException ex) {

            switch (ex.getStatusCode()) {

                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(ex));

                case UNPROCESSABLE_ENTITY :
                    throw new InvalidInputException(getErrorMessage(ex));

                default:
                    log.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
                    log.warn("Error body: {}", ex.getResponseBodyAsString());
                    throw ex;
            }
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex) {
            return ex.getMessage();
        }
    }

    @Override
    public List<Recommendation> getRecommendations(final int productId) {
        try {
            String url = recommendationServiceUrl + productId;

            log.debug("Will call getRecommendations API on URL: {}", url);
            List<Recommendation> recommendations = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Recommendation>>() {}).getBody();

            log.debug("Found {} recommendations for a product with id: {}", recommendations.size(), productId);
            return recommendations;

        } catch (Exception ex) {
            log.warn("Got an exception while requesting recommendations, return zero recommendations: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Review> getReviews(final int productId) {
        try {
            String url = reviewServiceUrl + productId;

            log.debug("Will call getReviews API on URL: {}", url);
            List<Review> reviews = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {}).getBody();

            log.debug("Found {} reviews for a product with id: {}", reviews.size(), productId);
            return reviews;

        } catch (Exception ex) {
            log.warn("Got an exception while requesting reviews, return zero reviews: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }
}
