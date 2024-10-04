package com.winggs.course.repo;

import com.winggs.course.modal.entity.PasswordReset;
import com.winggs.course.modal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepo extends JpaRepository<PasswordReset, Long> {
    PasswordReset findByOtp(String otp);
    Optional<PasswordReset> findByUser(User user);
}
