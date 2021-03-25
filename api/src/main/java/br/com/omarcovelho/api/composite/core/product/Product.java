package br.com.omarcovelho.api.composite.core.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Product {
    private int productId;
    private String name;
    private int weight;
    private String serviceAddress;
}
