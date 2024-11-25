package com.example.amazonclone.Service;

import com.example.amazonclone.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {
    ArrayList<Product> products = new ArrayList<>();

    private final UserService userServices;
    private final MerchantService merchantServices;

    public ArrayList<Product> getProducts(){
        return products;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public boolean updateProduct(String id, Product product){
        for (int i = 0; i < products.size(); i++) {
           if(products.get(i).getId().equals(id)){
               products.set(i, product);
               return true;
           }
        }
        return false;
    }

    public boolean deleteProduct(String id){
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getId().equals(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public void buyProduct(String userID, String productID, String merchantID){
        //1.check if all the given IDs are correct
        boolean allIDsCorrect = false;
        for (int i = 0; i < userServices.getUsers().size(); i++) {
            for (int j = 0; j < products.size() ; j++) {
                for (int k = 0; k < merchantServices.getMerchants().size(); k++) {
                    if(userServices.getUsers().get(i).getId().equals(userID)
                            && products.get(j).getId().equals(productID)
                            && merchantServices.getMerchants().get(k).getId().equals(merchantID)){
                        allIDsCorrect = true;

                    }
                }

            }

        }
        //2.check if merchant has the product in stock

    }

    //point 1. check if the product is in stock or not


}
