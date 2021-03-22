package br.com.omarcovelho.api.composite.core.review;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Review {
    private final int productId;
    private final int reviewId;
    private final String author;
    private final String subject;
    private final String content;
    private final String serviceAddress;
}
