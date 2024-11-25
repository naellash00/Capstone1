package com.example.amazonclone.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "Category ID Cannot Be Empty")
    private String id;

    @NotEmpty(message = "Category Name Cannot Be Empty")
    @Size(min = 3, message = "Name Cannot Be Less Than 3 Letters")
    private String name;
}
