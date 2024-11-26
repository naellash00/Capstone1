package com.example.amazonclone.Service;

import com.example.amazonclone.Model.Merchant;
import com.example.amazonclone.Model.MerchantStock;
import com.example.amazonclone.Model.Product;
import com.example.amazonclone.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();


    private final MerchantService merchantServices;
    private final ProductService productServices;
    private final UserService userServices;

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public int addMerchantStock(MerchantStock merchantStock) {
        //1 -> incorrect productID 2-> incorrect merchantID 3-> true
        for (Product product : productServices.getAllProducts()) {
            if (merchantStock.getProductID().equals(product.getId())) {
                for (Merchant merchant : merchantServices.getMerchants()) {
                    if (merchantStock.getMerchantID().equals(merchant.getId())) {
                        merchantStocks.add(merchantStock);
                        return 3;
                    } else return 2;
                }
            }
        }
        return 1;
    }

    public boolean updateMerchantStock(String id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStock(String id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }


    //Extra endpoint 1
    public boolean isProductInStock(String productID, String merchantID) {
        //1.check both IDs exist
        for (int i = 0; i < productServices.getAllProducts().size(); i++)
            for (int j = 0; j < merchantServices.getMerchants().size(); j++)
                if (productServices.getAllProducts().get(i).getId().equals(productID)
                        && merchantServices.getMerchants().get(i).getId().equals(merchantID)) {
                    //2.check if they are in the merchant stock
                    for (MerchantStock ms : merchantStocks) {
                        if (ms.getProductID().equals(productID) && ms.getMerchantID().equals(merchantID)) {
                            //3. check if product is in stock
                            if (ms.getStock() > 0) {
                                return true;
                            }
                        }
                    }
                }

        return false;
    }


    public boolean isValidProductID(String productID) {
        for (Product product : productServices.products) {
            if (product.getId().equals(productID)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidMerchantID(String merchantID) {
        for (Merchant merchant : merchantServices.getMerchants()) {
            if (merchant.getId().equals(merchantID)) {
                return true;
            }
        }
        return false;
    }

    public void reduceStock(String merchantID) {
        for (MerchantStock ms : merchantStocks) {
            if (ms.getMerchantID().equals(merchantID)) {
                ms.setStock(ms.getStock() - 1);
            }
        }
    }

    public int addStockToProduct(String productID, String merchantID, int additionalStock) {
        if (isValidProductID(productID)) {
            if (isValidMerchantID(merchantID)) {
                for (MerchantStock ms : merchantStocks) {
                    if (ms.getProductID().equals(productID) && ms.getMerchantID().equals(merchantID)) {
                        ms.setStock(ms.getStock() + additionalStock);
                        return 4;
                    } else return 3;

                }
            } else return 2; // invalid merchant id
        }
        return 1; // incorrect product id
    }

    public int buyProduct(String userID, String productID, String merchantID) {
        //check if all the given ids are valid or not
        if (userServices.isValidUserID(userID) && isValidProductID(productID) && isValidMerchantID(merchantID)) {
            if (isValidProductID(productID)) {
                if (isValidMerchantID(merchantID)) {

                    //check if the merchant has the product in stock or return bad request.
                    if (isProductInStock(productID, merchantID)) {
                        for (Product product : productServices.getAllProducts()) {
                            //if balance is less than the product price returns bad request.
                            if (userServices.checkUserBalance(userID, product.getPrice())) {
                                //deducted the price of the product from the user balance.
                                userServices.deductPriceFromUserBalance(userID, product.getPrice());
                                //reduce the stock from the MerchantStock.
                                reduceStock(merchantID);
                                return 6; // successfully completed
                            } else return 5; // bad request balance is not enough
                        }
                    } else return 4; // product out of stock
                } else return 3; // incorrect merchant id
            } else return 2; // incorrect product id
        }
        return 1; // incorrect user IDs
    }


    public int updateProductPrice(String productID, String merchantID, double newPrice) {
        //check correct merchant id
        if (!isValidMerchantID(merchantID))
            return 1; //invalid merchant id

        for (MerchantStock ms : merchantStocks) {
            if (ms.getProductID().equals(productID) && ms.getMerchantID().equals(merchantID)) {
                for (Product product : productServices.getAllProducts()) {
                    if (product.getId().equals(productID)) {
                        product.setPrice(newPrice);
                        return 0;
                    }
                }
            }
        }
        return 2; //product id not in stock
    }


}
