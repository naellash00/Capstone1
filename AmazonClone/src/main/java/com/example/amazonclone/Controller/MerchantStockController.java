package com.example.amazonclone.Controller;

import com.example.amazonclone.ApiResponse.ApiResponse;
import com.example.amazonclone.Model.MerchantStock;
import com.example.amazonclone.Service.MerchantService;
import com.example.amazonclone.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant/stock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockServices;

    @GetMapping("/get")
    public ResponseEntity getMerchantStocks(){
        ArrayList<MerchantStock> merchantStocks = merchantStockServices.getMerchantStocks();
        return ResponseEntity.status(200).body(merchantStocks);
    }

    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        merchantStockServices.addMerchantStock(merchantStock);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock Added Successfully"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors){
        boolean isUpdated = merchantStockServices.updateMerchantStock(id, merchantStock);
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if(isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock Updated Successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock ID Not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable String id){
        boolean isDeleted = merchantStockServices.deleteMerchantStock(id);
        if(isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock Deleted Successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock ID Not Found"));
    }

    @PutMapping("/add/product/stock/{productid}/{merchantid}/{additionalstock}")
    public ResponseEntity addStockToProduct(@PathVariable String productid, @PathVariable String merchantid, @PathVariable int additionalstock){
        //int result = merchantStockServices.addStockToProduct(productID, merchantID, additionalStock);
        boolean stockIsUpdated = merchantStockServices.addStockToProduct(productid, merchantid, additionalstock);
        if(stockIsUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Additional Product Stock Added Successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Product ID and Merchant ID Not Found"));
    }




}
