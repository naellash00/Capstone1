package com.example.amazonclone.Controller;

import com.example.amazonclone.ApiResponse.ApiResponse;
import com.example.amazonclone.Model.Merchant;
import com.example.amazonclone.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantServices;

    @GetMapping("/get")
    public ResponseEntity getMerchants(){
        ArrayList<Merchant> merchants = merchantServices.getMerchants();
        return ResponseEntity.status(200).body(merchants);
    }

    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        merchantServices.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors){
        boolean isUpdated = merchantServices.updateMerchant(id, merchant);
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if(isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Updated Successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant ID Not Found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable String id){
        boolean isDeleted = merchantServices.deleteMerchant(id);
        if(isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Deleted Successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant ID Not Found"));
    }



}
