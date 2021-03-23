package br.com.omarcovelho.core.product.services;

import br.com.omarcovelho.api.composite.core.product.Product;
import br.com.omarcovelho.api.composite.core.product.ProductService;
import br.com.omarcovelho.util.exceptions.InvalidInputException;
import br.com.omarcovelho.util.exceptions.NotFoundException;
import br.com.omarcovelho.util.http.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ServiceUtil serviceUtil;

    @Override
    public Product getProduct(int productId) {
        log.debug("/product return the found product for productId={}", productId);

        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

        if (productId == 13) throw new NotFoundException("No product found for productId: " + productId);

        return new Product(productId, "name-" + productId, 123, serviceUtil.getServiceAddress());
    }
}