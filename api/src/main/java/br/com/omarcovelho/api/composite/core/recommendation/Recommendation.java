package br.com.omarcovelho.api.composite.core.recommendation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Recommendation {
    private final int productId;
    private final int recommendationId;
    private final String author;
    private final int rate;
    private final String content;
    private final String serviceAddress;
}
