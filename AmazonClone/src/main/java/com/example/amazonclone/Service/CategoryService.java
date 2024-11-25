package com.example.amazonclone.Service;

import com.example.amazonclone.Model.Category;
import com.example.amazonclone.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CategoryService {
    ArrayList<Category> categories = new ArrayList<>();
   // private final ProductService productService;

    public ArrayList<Category> getCategories(){
        return categories;
    }

    public void addCategory(Category category){
        categories.add(category);
    }

    public boolean updateCategory(String id, Category category){
        for (int i = 0; i < categories.size() ; i++) {
            if(categories.get(i).getId().equals(id)){
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id){
        for (int i = 0; i < categories.size() ; i++) {
            if(categories.get(i).getId().equals(id)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }

//   public ArrayList<Product> getProductsByCategory(String categoryID){
//        ArrayList<Product> sameProductCategory = new ArrayList<>();
//        for(Product product : productService.getAllProducts()){
//            if(product.getCategoryID().equals(categoryID)){
//                sameProductCategory.add(product);
//            }
////            else
////                return null; // category id not found
//        }
//        return sameProductCategory;
//   }
}
