package com.example.amazonclone.Controller;


import com.example.amazonclone.ApiResponse.ApiResponse;
import com.example.amazonclone.Model.User;
import com.example.amazonclone.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userServices;

    @GetMapping("/get")
    public ResponseEntity getUsers(){
        ArrayList<User> users = userServices.getUsers();
        return ResponseEntity.status(200).body(users);
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        userServices.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable String id, @RequestBody User user, Errors errors){
        boolean isUpdated = userServices.updateUser(id, user);
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if(isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("User Updated Successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("User ID Not Found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
        boolean isDeleted = userServices.deleteUser(id);

        if(isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("User Deleted Successfully"));

        return ResponseEntity.status(400).body(new ApiResponse(" User ID Not Found"));
    }

    //Extra Endpoint 4
    @PutMapping("/send/gift/{senderuserid}/{receiverusername}/{moneygiftamount}")
    public ResponseEntity sendGift(@PathVariable String senderuserid, @PathVariable String receiverusername, @PathVariable double moneygiftamount){
        int sendingGiftResult = userServices.sendGift(senderuserid, receiverusername, moneygiftamount);
        if(sendingGiftResult == 1)
            return ResponseEntity.status(400).body(new ApiResponse("User ID Not Found"));
        else if(sendingGiftResult == 2)
            return ResponseEntity.status(400).body(new ApiResponse("Your Balance Not Enough"));
        else if(sendingGiftResult == 4)
            return ResponseEntity.status(400).body(new ApiResponse("Incorrect Username"));

        return ResponseEntity.status(200).body(new ApiResponse("Gift Sent Successfully"));

    }

}
