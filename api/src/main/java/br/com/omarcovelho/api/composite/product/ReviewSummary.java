package br.com.omarcovelho.api.composite.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
public class ReviewSummary {

    private final int reviewId;
    private final String author;
    private final String subject;
    private final String content;
}