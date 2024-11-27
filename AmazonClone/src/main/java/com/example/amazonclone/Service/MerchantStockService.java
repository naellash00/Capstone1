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

    // this list is a record of all the purchased product with the user who bought them
    ArrayList<String> purchaseList = new ArrayList<>();

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

    public boolean isProductInStock(String productID, String merchantID) {
        //1.check both IDs exist
        if (isValidProductID(productID) && isValidMerchantID(merchantID)) {
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
        if (userServices.isValidUserID(userID)) {
            if (isValidProductID(productID)) {
                if (isValidMerchantID(merchantID)) {

                    //check if the merchant has the product in stock or return bad request.
                    if (isProductInStock(productID, merchantID)) {
                        for (Product product : productServices.getAllProducts()) {
                            //**
                            if (product.getId().equals(productID)) {
                                //if balance is less than the product price returns bad request.
                                if (userServices.checkUserBalance(userID, product.getPrice())) {
                                    //deducted the price of the product from the user balance.
                                    userServices.deductPriceFromUserBalance(userID, product.getPrice());
                                    //reduce the stock from the MerchantStock.
                                    reduceStock(merchantID);

                                    //**for helper Method**
                                    purchaseList.add(userID + "-" + productID);

                                    return 6; // successfully completed
                                } else return 5; // bad request balance is not enough
                            }
                        }
                    } else return 4; // product out of stock
                } else return 3; // incorrect merchant id
            } else return 2; // incorrect product id
        }
        return 1; // incorrect user IDs
    }

    public boolean isPurchased(String userID, String productID) {
        // check of the correct IDs
        if (isValidProductID(productID) && userServices.isValidUserID(userID)) {
            for (String query : purchaseList) {
                // check if they are bought or not
                if (query.equals(userID + "-" + productID)) {
                    return true;
                }
            }
        }
        return false; // incorrect IDs
    }

    public ArrayList<Product> getUserPurchaseHistory(String userID) {
        ArrayList<Product> thePurchasedProducts = new ArrayList<>();
        // check if the user exist
        if (userServices.isValidUserID(userID)) {
            // add the product to the user purchased list
            for (Product product : productServices.getAllProducts()) {
                if (isPurchased(userID, product.getId())) {
                    thePurchasedProducts.add(product);
                }
            }
        }
        return thePurchasedProducts;
    }


}
