package br.com.omarcovelho.api.composite.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class ReviewSummary {

    private final int reviewId;
    private final String author;
    private final String subject;
}
