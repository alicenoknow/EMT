package com.agh.emt.service.authentication;

import com.agh.emt.model.student.Student;
import com.agh.emt.model.student.StudentRepository;
import com.agh.emt.service.student.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final StudentRepository studentRepository;

    public UserDetails loadByEmail(String email) throws StudentNotFoundException {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new StudentNotFoundException("Nie znaleziono użytkownika o mailu: " + email));
        return UserDetailsImpl.build(student);
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
