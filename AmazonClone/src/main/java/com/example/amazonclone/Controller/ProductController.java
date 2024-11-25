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
    public ResponseEntity getProducts() {
        ArrayList<Product> products = productServices.getAllProducts();
        return ResponseEntity.status(200).body(products);
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        boolean isAdded = productServices.addProduct(product);
        if (isAdded)
            return ResponseEntity.status(200).body(new ApiResponse("Product Added Successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Category ID Dose Not Exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors) {
        boolean isUpdated = productServices.updateProduct(id, product);
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("Product Updated Successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Product ID Not Found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
        boolean isDeleted = productServices.deleteProduct(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("Product Deleted Successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Product ID Not Found"));
    }

    // Extra endpoint 2
    @PutMapping("/discount/{merchantid}/{productid}/{discountpercentage}")
    public ResponseEntity applyDiscount(@PathVariable String merchantid, @PathVariable String productid, @PathVariable double discountpercentage) {
        int discountResult = productServices.applyDiscount(merchantid, productid, discountpercentage);
        if (discountResult == 1)
            return ResponseEntity.status(400).body(new ApiResponse("Only Merchant Can Apply Discount On Product"));
        else if (discountResult == 2)
            return ResponseEntity.status(400).body(new ApiResponse("Product ID Not Found"));

        return ResponseEntity.status(200).body(new ApiResponse("Discount Applied Successfully"));
    }

    //Extra endpoint 3
    @GetMapping("/same/product/category/{categoryid}")
    public ResponseEntity getProductsByCategory(@PathVariable String categoryid) {
        ArrayList<Product> sameCategoryProducts = productServices.getProductsByCategory(categoryid);
        if (sameCategoryProducts.isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("Category ID Not Found"));

        return ResponseEntity.status(200).body(sameCategoryProducts);
    }

    //Extra Endpoint 5
    @PostMapping("/add/review/{productid}/{userid}/{review}")
    public ResponseEntity addReview(@PathVariable String productid, @PathVariable String userid, @PathVariable String review) {
        int reviewResult = productServices.addReview(productid, userid, review);
        if (reviewResult == 1)
            return ResponseEntity.status(400).body(new ApiResponse("Product ID Not Found"));
        else if (reviewResult == 2)
            return ResponseEntity.status(400).body(new ApiResponse("User ID Not Found"));
        else if (reviewResult == 3)
            return ResponseEntity.status(400).body(new ApiResponse("Review Must Be Longer Than 5 Letters"));

        return ResponseEntity.status(200).body(new ApiResponse("Review Added Successfully"));

    }




}
