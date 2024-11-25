package com.example.amazonclone.Service;

import com.example.amazonclone.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers(){
        return users;
    }

    public void addUser (User user){
        users.add(user);
    }

    public boolean updateUser(String id, User user){
        for (int i = 0; i < users.size() ; i++) {
           if(users.get(i).getId().equals(id)){
               users.set(i, user);
               return true;
           }
        }
        return false;
    }

    public boolean deleteUser(String id){
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(id)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean isValidUserID(String userID){
        for(User user : users){
            if(user.getId().equals(userID)){
                return true;
            }
        }
        return false;
    }
    public boolean checkUserBalance(String userID, double amount){
        for(User user : users){
            if(user.getId().equals(userID)){
                if(user.getBalance() >= amount){
                    return true;
                }
            }
        }
        return false;
    }

    public void deductPriceFromUserBalance(String userID, double productPrice){
        for(User user : users){
            if(user.getId().equals(userID)){
                user.setBalance(user.getBalance() - productPrice);
            }
        }
    }

}
