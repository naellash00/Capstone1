package com.example.amazonclone.Service;

import com.example.amazonclone.Model.Product;
import com.example.amazonclone.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    ArrayList<User> users = new ArrayList<>();


    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean updateUser(String id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean isValidUserID(String userID) {
        for (User user : users) {
            if (user.getId().equals(userID)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkUserBalance(String userID, double amount) {
        for (User user : users) {
            if (user.getId().equals(userID)) {
                if (user.getBalance() >= amount) {
                    return true;
                }
            }
        }
        return false;
    }

    public void deductPriceFromUserBalance(String userID, double productPrice) {
        for (User user : users) {
            if (user.getId().equals(userID)) {
                user.setBalance(user.getBalance() - productPrice);
            }
        }
    }

    public int sendGift(String senderUserID, String receiverUsername, double moneyGiftAmount) {
        boolean canSend = false;
        for (User user : users) {
            if (user.getId().equals(senderUserID)) {
                if (user.getBalance() >= moneyGiftAmount) {
                    canSend = true;
                } else return 2; // balance not enough
            }

        }
        if (!canSend) // if cansend is still false, then it didnt even find the sender id
            return 1; //incorrect id

        for (User user : users) {
            if (user.getUsername().equals(receiverUsername)) {
                if (canSend) {
                    deductPriceFromUserBalance(senderUserID, moneyGiftAmount);
                    user.setBalance(user.getBalance() + moneyGiftAmount);
                    return 3; // gift sent successfully
                }
            }
        }
        return 4; //incorrect username
    }





}
