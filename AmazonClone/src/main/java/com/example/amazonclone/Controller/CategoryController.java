package com.example.amazonclone.Controller;

import com.example.amazonclone.ApiResponse.ApiResponse;
import com.example.amazonclone.Model.Category;
import com.example.amazonclone.Model.Product;
import com.example.amazonclone.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryServices;

    @GetMapping("/get")
    public ResponseEntity getCategories() {
        ArrayList<Category> categories = categoryServices.getCategories();
        return ResponseEntity.status(200).body(categories);
    }

    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        categoryServices.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("Category Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors) {
        boolean isUpdated = categoryServices.updateCategory(id, category);
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("Catgory Updated Successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Category ID Not Found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id) {
        boolean isDeleted = categoryServices.deleteCategory(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("Category Deleted Successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Category ID Not Found"));
    }


}
