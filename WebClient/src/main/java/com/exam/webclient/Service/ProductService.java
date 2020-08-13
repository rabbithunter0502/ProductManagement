package com.exam.webclient.Service;

import com.exam.webclient.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Value("${spring.datasource.url}")
    private String resource;
    @Value("${spring.datasource.url}/products/{id}")
    private String idResource;
    @Autowired
    private RestTemplate restTemplate;

    public List<Product> findAll() {
        return Arrays.stream(restTemplate.getForObject(resource, Product[].class)).collect(Collectors.toList());
    }

    public Product update(Long id, Product product) {
        return restTemplate.exchange(idResource, HttpMethod.PUT, new HttpEntity<>(product), Product.class, id).getBody();
    }

    public void delete(Long id) {
        restTemplate.delete(idResource, id);
    }

    public Product create(Product product) {
        return restTemplate.postForObject(resource, product, Product.class);
    }
}

