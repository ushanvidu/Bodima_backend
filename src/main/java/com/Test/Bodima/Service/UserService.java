package com.Test.Bodima.Service;

import com.Test.Bodima.Model.User;
import com.Test.Bodima.Repo.UserRepository; // ✅ Correct import
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor Injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRoom(Integer roomId) {
        return userRepository.findByRoomRoomId(roomId);
    }

    public User updateUser(Integer id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(userDetails.getName());
                    user.setEmail(userDetails.getEmail());
                    user.setPhone(userDetails.getPhone());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}