package de.elvirakraft.docmanagement.services;

import de.elvirakraft.docmanagement.helpers.CRUDService;
import de.elvirakraft.docmanagement.models.User;
import de.elvirakraft.docmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.LongToIntFunction;

@Service
public class UserService extends CRUDService<User, UserRepository> {
    @Autowired
    UserRepository userRepository;

    public User createUser(User user){
        userRepository.save(user);
        return userRepository.findById(user.getId()).get();
    }

    public User getUserByID(Long id){
        return userRepository.findById(id).get();
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User updateUser(User user){
        userRepository.save(user);
        return user;
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }
}
