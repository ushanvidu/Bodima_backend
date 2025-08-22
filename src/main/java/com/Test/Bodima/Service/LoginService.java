package com.Test.Bodima.Service;

import com.Test.Bodima.Model.Login;
import com.Test.Bodima.Repo.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    public Login createLogin(Login login) {
        return loginRepository.save(login);
    }

    public Optional<Login> getLoginById(Integer id) {
        return loginRepository.findById(id);
    }

    public Optional<Login> getLoginByUsername(String username) {
        return loginRepository.findByUsername(username);
    }

    public Login updateLogin(Integer id, Login loginDetails) {
        return loginRepository.findById(id)
                .map(login -> {
                    login.setUsername(loginDetails.getUsername());
                    login.setPassword(loginDetails.getPassword());
                    return loginRepository.save(login);
                })
                .orElseThrow(() -> new RuntimeException("Login not found with id: " + id));
    }

    public void deleteLogin(Integer id) {
        loginRepository.deleteById(id);
    }
}