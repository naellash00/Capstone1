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

}
