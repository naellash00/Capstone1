package com.example.amazonclone.Service;

import com.example.amazonclone.Model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {


    ArrayList<Product> products = new ArrayList<>();
    private final CategoryService categoryServices;
    private final MerchantService merchantServices;
    private final UserService userService;


    public ArrayList<Product> getAllProducts() {
        return products;
    }

    public boolean addProduct(Product product) {
        for (Category category : categoryServices.getCategories()) {
            if (product.getCategoryID().equals(category.getId())) {
                products.add(product);
                return true;
            }
        }
        return false;
    }

    public boolean updateProduct(String id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public int applyDiscount(String merchantID, String productID, double discountPercentage) {
        for (Merchant merchant : merchantServices.getMerchants()) {
            if (merchant.getId().equals(merchantID)) {
                for (Product product : products) {
                    if (product.getId().equals(productID)) {
                        product.setPrice(product.getPrice() - (product.getPrice() * (discountPercentage / 100)));
                        return 3; // discount applied
                    }
                }
                return 2; // product id incorrect
            }
        }
        return 1; //merchant id incorrect
    }


    public int addReview(String productID, String userID, String review) {
        //check product id
        for (Product product : products) {
            if (product.getId().equals(productID)) {
                // check user id
                for (User user : userService.getUsers()) {
                    if (user.getId().equals(userID)) {
                        if (review.length() >= 5) {
                            // empty list issue
                            if (product.getReviews() == null) {
                                product.setReviews(new ArrayList<>());
                            }
                            product.getReviews().add(review);
                            return 0;
                        }
                        return 3; // review length must be longer than 5
                    }

                }
                return 2; //user id not found
            }
        }
        return 1; //product id not found
    }

    public ArrayList<String> getProductReviews(String productID) {
        for (Product product : products) {
            if (product.getId().equals(productID)) {
                return product.getReviews(); // return  list
            }
        }
        return null; //  product not found
    }

    public Product findProduct(String productID) {
        for (Product product : products) {
            if (product.getId().equals(productID)) {
                return product;
            }
        }
        return null;
    }

    public int addProductToFavorite(String userID, String productID) {
        for (User user : userService.getUsers()) {
            if (user.getId().equals(userID)) {
                //empty list issue
                if (user.getFavoriteProducts() == null) {
                    user.setFavoriteProducts(new ArrayList<>());
                }


                for (Product product : user.getFavoriteProducts()) {

                    if (product.getId().equals(productID))
                        return 1; // product already in favorites
                }

                Product productToAdd = findProduct(productID);

                if (productToAdd != null) {
                    user.getFavoriteProducts().add(productToAdd);
                    return 2; //product added successfully
                }

            }
        }
        return 3; //invalid user id
    }

    public ArrayList<Product> getUserFavoriteProducts(String userID) {
        for (User user : userService.getUsers()) {
            if (user.getId().equals(userID)) {
                return user.getFavoriteProducts();
            }
        }
        return new ArrayList<>(); // User not found
    }


}




