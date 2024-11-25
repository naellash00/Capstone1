package com.example.amazonclone.Controller;

import com.example.amazonclone.ApiResponse.ApiResponse;
import com.example.amazonclone.Model.Product;
import com.example.amazonclone.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productServices;

    @GetMapping("/get")
    public ResponseEntity getProducts(){
        ArrayList<Product> products = productServices.getProducts();
        return ResponseEntity.status(200).body(products);
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        productServices.addProduct(product);
        return ResponseEntity.status(200).body(new ApiResponse("Product Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors){
        boolean isUpdated = productServices.updateProduct(id, product);
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if(isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("Product Updated Successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Product ID Not Found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id){
        boolean isDeleted = productServices.deleteProduct(id);
        if(isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("Product Deleted Successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Product ID Not Found"));
    }










}
