package com.agh.emt.service.authentication;

import com.agh.emt.model.admin.Admin;
import com.agh.emt.model.admin.AdminRepository;
import com.agh.emt.model.student.Student;
import com.agh.emt.model.student.StudentRepository;
import com.agh.emt.utils.authentication.InconclusiveUsernameException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Student> student;
        Optional<Admin> admin;

        admin = adminRepository.findByEmail(email);
        student = studentRepository.findByEmail(email);

        if (admin.isPresent() && student.isPresent()) {
            throw new InconclusiveUsernameException("BŁĄÐ APLIKACJI - znaleziono wielu użytkowników o mailu: " + email + ", zgłoś to do administratora systemu");
        }

        if (student.isPresent()) {
            return UserDetailsImpl.build(student.get());
        } else if (admin.isPresent()) {
            return UserDetailsImpl.build(admin.get());
        } else {
            throw new UsernameNotFoundException("Nie znaleziono użytkownika o mailu: " + email);
        }
    }
}
