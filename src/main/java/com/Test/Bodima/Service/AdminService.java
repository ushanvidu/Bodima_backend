package com.Test.Bodima.Service;

import com.Test.Bodima.Model.Admin;
import com.Test.Bodima.Repo.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Optional<Admin> getAdminById(Integer id) {
        return adminRepository.findById(id);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin updateAdmin(Integer id, Admin adminDetails) {
        return adminRepository.findById(id)
                .map(admin -> {
                    admin.setName(adminDetails.getName());
                    admin.setEmail(adminDetails.getEmail());
                    return adminRepository.save(admin);
                })
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
    }

    public void deleteAdmin(Integer id) {
        adminRepository.deleteById(id);
    }
}