package com.winggs.course.service.impl;

import com.winggs.course.modal.common.ApplicationException;
import com.winggs.course.modal.common.ErrorCode;
import com.winggs.course.modal.dto.UserDto;
import com.winggs.course.modal.entity.PasswordReset;
import com.winggs.course.modal.entity.Student;
import com.winggs.course.modal.entity.User;
import com.winggs.course.modal.enums.Role;
import com.winggs.course.modal.payload.ChangePasswordPayload;
import com.winggs.course.modal.payload.CreateUserPayload;
import com.winggs.course.repo.PasswordResetRepo;
import com.winggs.course.repo.SchoolRepo;
import com.winggs.course.repo.StudentRepo;
import com.winggs.course.repo.UserRepo;
import com.winggs.course.service.UploadService;
import com.winggs.course.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    private final SchoolRepo schoolRepo;
    private final StudentRepo studentRepo;
    private final UploadService uploadService;
    private final PasswordResetRepo passwordResetRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto create(CreateUserPayload payload) {
        userRepository.findUserByEmail(payload.username()).ifPresent(user -> {
            throw new ApplicationException(ErrorCode.BAD_REQUEST, "Given Email Already Exists");
        });
        User user = new User();
        user.setFirstName(payload.firstName());
        user.setLastName(payload.lastName());
        user.setEmail(payload.username());
        user.setPassword(passwordEncoder.encode(payload.password()));
        user.setMobile(payload.mobile());
        user.setRole(Role.STUDENT);
        user.setCreatedDate(Instant.now());
        user.setModifiedDate(Instant.now());
        User save = userRepository.save(user);

        Student student = new Student();
        student.setUser(save);
        student.setDob(payload.dob());
        student.setGender(payload.gender());
        student.setPermitNo(payload.permitNo());
        student.setFee((double) 0);
        student.setCreatedDate(Instant.now());
        student.setModifiedDate(Instant.now());
        Student save1 = studentRepo.save(student);
        return UserDto.of(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateCredentials(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
//        return userRepository.findUserByMobile(username);
        return userRepository.findUserByEmail(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getMyProfile(String userId) {
        return userRepository.findById(userId).map(UserDto::of);
    }

    @Override
    @Transactional
    public void uploadProfilePic(String id, MultipartFile file) throws IOException {
        String upload = uploadService.upload(file);

        userRepository.findById(id).map(user -> {
            user.setImageUrl(upload);
            return user;
        });
    }

    @Override
    @Transactional
    public Optional<UserDto> updateMyProfile(String userId, UserDto userDto) {

        return userRepository.findById(userId).map(user -> {
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setMobile(userDto.getMobile());
            user.setImageUrl(userDto.getImageUrl());
            user.setModifiedDate(Instant.now());
            return user;
        }).map(UserDto::of);
    }

    @Override
    @Transactional
    public boolean checkIfUserExists(String username) {
        Optional<User> user = userRepository.findUserByEmail(username);
        return user.isEmpty();
    }

    @Override
    @Transactional
    public void changeUserPassword(String userId, ChangePasswordPayload payload) {
        userRepository.findById(userId).ifPresent(user -> {
            String oldEncodedPassword = user.getPassword();
            String presentedRawPassword = payload.oldPassword();
            var isCredentialsValid = validateCredentials(presentedRawPassword, oldEncodedPassword);
            //Todo : fix if else statement
            if (isCredentialsValid) {
                if (!Objects.equals(presentedRawPassword, payload.newPassword())) {
                    user.setPassword(passwordEncoder.encode(payload.newPassword()));
                }
            } else {
                throw new ApplicationException(ErrorCode.BAD_REQUEST, "Invalid password");
            }
        });
    }

    @Override
    @Transactional
    public void forgotUserPassword(String username) {

    }

    @Override
    public String checkPasswordRestOtp(String otp) {
        final PasswordReset passwordReset = passwordResetRepo.findByOtp(otp);
        return !isOtpFound(passwordReset) ? "invalidOtp"
                : isOtpExpired(passwordReset) ? "expired"
                : "valid";
    }

    private boolean isOtpFound(PasswordReset passwordOtp) {
        return passwordOtp != null;
    }

    private boolean isOtpExpired(PasswordReset passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
