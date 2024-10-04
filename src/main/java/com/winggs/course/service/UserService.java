package com.winggs.course.service;


import com.winggs.course.modal.dto.UserDto;
import com.winggs.course.modal.entity.User;
import com.winggs.course.modal.payload.ChangePasswordPayload;
import com.winggs.course.modal.payload.CreateUserPayload;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {

    UserDto create(CreateUserPayload payload);

    boolean validateCredentials(String rawPassword, String encodedPassword);

    Optional<User> findByUsername(String username);

    Optional<UserDto> getMyProfile(String userId);

    void uploadProfilePic(String id, MultipartFile file) throws IOException;

    Optional<UserDto> updateMyProfile(String userId, UserDto userDto);

    boolean checkIfUserExists(String username);

    void changeUserPassword(String userId, ChangePasswordPayload payload);

    void forgotUserPassword(String username);

    String checkPasswordRestOtp(String otp);
}
