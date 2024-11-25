package com.example.amazonclone.Service;

import com.example.amazonclone.Model.MerchantStock;
import com.example.amazonclone.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    private final MerchantService merchantServices;
    private final ProductService productServices;

    public ArrayList<MerchantStock> getMerchantStocks(){
        return merchantStocks;
    }

    public void addMerchantStock(MerchantStock merchantStock){
        merchantStocks.add(merchantStock);
    }

    public boolean updateMerchantStock(String id, MerchantStock merchantStock){
        for (int i = 0; i < merchantStocks.size() ; i++) {
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStock(String id){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean addStockToProduct(String productID, String merchantID, int additionalStock){
       // 1 -> ended successfully 2 -> incorrect IDs 3-> IDs not in merchant stock
        for (int i = 0; i < productServices.getProducts().size(); i++) {
            for (int j = 0; j < merchantServices.getMerchants().size(); j++) {
                if(productServices.getProducts().get(i).getId().equals(productID)
                        && merchantServices.getMerchants().get(j).getId().equals(merchantID)){
                    for(MerchantStock ms : merchantStocks){
                        if(ms.getProductID().equals(productID) && ms.getMerchantID().equals(merchantID)){
                            ms.setStock(ms.getStock() + additionalStock);
                           // return 1;
                            return true;
                        }
                        //else  return 3;
                    }
                }
            }
        }
        ///return 2;
        return false;
    }

    //pont 1. is product in stock or not
    public boolean isProductInStock(String productID, String merchantID){
        //1.check both IDs exist
        for (int i = 0; i < productServices.getProducts().size(); i++) {
            for (int j = 0; j < merchantServices.getMerchants().size(); j++) {
                if(productServices.getProducts().get(i).getId().equals(productID)
                        && merchantServices.getMerchants().get(i).getId().equals(merchantID)){
                    //2.check if they are in the merchant stock
                    for(MerchantStock ms : merchantStocks){
                        if(ms.getProductID().equals(productID) && ms.getMerchantID().equals(merchantID)){
                            //3. check if product is in stock
                            if(ms.getStock() > 0){
                                return true;
                            }
                        }
                    }
                }
            }

        }

        return false;
    }

    //2.check if product id and merchant id are correct and inside the stock
   



}
