package com.example.amazonclone.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "ID Cannot Be Empty")
    private String id;

    @NotEmpty(message = "Product ID Cannot Be Empty")
    private String productID;

    @NotEmpty(message = "Merchant ID Cannot Be Empty")
    private String merchantID;

    @NotNull(message = "Stock Cannot Be Empty")
    @Min(value = 10, message = "Stock Cannot Be Less Than 10")
    private int stock;
}
