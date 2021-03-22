/*
 * @(#)ProductCompositeServiceImpl.java 1.0 22/03/2021
 *
 * Copyright (c) 2021, Embraer. All rights reserved. Embraer S/A
 * proprietary/confidential. Use is subject to license terms.
 */
package br.com.omarcovelho.microservices.composite.product.services;

import br.com.omarcovelho.api.composite.core.product.Product;
import br.com.omarcovelho.api.composite.core.recommendation.Recommendation;
import br.com.omarcovelho.api.composite.core.review.Review;
import br.com.omarcovelho.api.composite.product.ProductAggregate;
import br.com.omarcovelho.api.composite.product.ProductCompositeService;
import br.com.omarcovelho.api.composite.product.RecommendationSummary;
import br.com.omarcovelho.api.composite.product.ReviewSummary;
import br.com.omarcovelho.api.composite.product.ServiceAddresses;
import br.com.omarcovelho.util.http.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author marprado - Marco Prado
 * @version 1.0 22/03/2021
 */
@RestController
@RequiredArgsConstructor
public class ProductCompositeServiceImpl implements ProductCompositeService {

    private final ServiceUtil serviceUtil;
    private final ProductCompositeIntegration integration;

    @Override
    public ProductAggregate getProduct(final int productId) {
        Product product = integration.getProduct(productId);
        List<Recommendation> recommendations = integration.getRecommendations(productId);
        List<Review> reviews = integration.getReviews(productId);
        return createProductAggregate(product, recommendations, reviews, serviceUtil.getServiceAddress());
    }

    private ProductAggregate createProductAggregate(Product product, List<Recommendation> recommendations, List<Review> reviews, String serviceAddress) {

        // 1. Setup product info
        int productId = product.getProductId();
        String name = product.getName();
        int weight = product.getWeight();

        // 2. Copy summary recommendation info, if available
        List<RecommendationSummary> recommendationSummaries = (recommendations == null) ? null :
                recommendations.stream()
                        .map(r -> new RecommendationSummary(r.getRecommendationId(), r.getAuthor(), r.getRate()))
                        .collect(Collectors.toList());

        // 3. Copy summary review info, if available
        List<ReviewSummary> reviewSummaries = (reviews == null)  ? null :
                reviews.stream()
                        .map(r -> new ReviewSummary(r.getReviewId(), r.getAuthor(), r.getSubject()))
                        .collect(Collectors.toList());

        // 4. Create info regarding the involved microservices addresses
        String productAddress = product.getServiceAddress();
        String reviewAddress = (reviews != null && reviews.size() > 0) ? reviews.get(0).getServiceAddress() : "";
        String recommendationAddress = (recommendations != null && recommendations.size() > 0) ? recommendations.get(0).getServiceAddress() : "";
        ServiceAddresses serviceAddresses = new ServiceAddresses(serviceAddress, productAddress, reviewAddress, recommendationAddress);

        return new ProductAggregate(productId, name, weight, recommendationSummaries, reviewSummaries, serviceAddresses);
    }
}
