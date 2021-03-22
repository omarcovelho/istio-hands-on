package br.com.omarcovelho.api.composite.core.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Product {
    private final int productId;
    private final String name;
    private final int weight;
    private final String serviceAddress;
}
