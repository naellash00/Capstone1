package com.example.amazonclone.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "ID Cannot Be Empty")
    private String id;

    @NotEmpty(message = "Name Cannot Be Empty")
    @Size(min = 3, message = "Name Must Be Longer Than 3 Letters")
    private String name;

    @NotNull(message = "Price Cannot Be Empty")
    @Min(value = 0, message = "Price Cannot Be Less Than Zero")
    private double price;

    @NotEmpty(message = "Category ID Cannot Be Empty")
    private String categoryID;

}
