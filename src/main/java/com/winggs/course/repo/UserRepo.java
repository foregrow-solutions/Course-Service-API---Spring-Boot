package com.winggs.course.repo;

import com.winggs.course.modal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByMobile(String mobile);

}
