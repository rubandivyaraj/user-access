package com.security.user.access.controller;

import com.security.user.access.response.ApiResponseTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private List<String> product = new ArrayList<>(List.of("Apple", "Bat", "Computer"));

    @GetMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseTO<List<String>>> getProduct() {

        ApiResponseTO<List<String>> apiResponseTO = new ApiResponseTO<>(HttpStatus.ACCEPTED, "Success", product);
        return ResponseEntity.ok(apiResponseTO);
    }

    @PostMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseTO<List<String>>> postProduct(@RequestBody List<String> productList) {
        product.addAll(productList);
        ApiResponseTO<List<String>> apiResponseTO = new ApiResponseTO<>(HttpStatus.ACCEPTED, "Success", product);
        return ResponseEntity.ok(apiResponseTO);
    }

}
