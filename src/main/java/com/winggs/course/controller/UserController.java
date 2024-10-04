package com.winggs.course.controller;

import com.winggs.course.modal.dto.UserDto;
import com.winggs.course.modal.payload.ChangePasswordPayload;
import com.winggs.course.security.AuthenticatedUser;
import com.winggs.course.security.CurrentUser;
import com.winggs.course.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Tag(name = "User APIs", description = "API for manage User Profile Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get User Profile Details")
    @GetMapping(value = {"/me", "/users/{id}"})
    public Optional<UserDto> get(@CurrentUser AuthenticatedUser user) {
        return userService.getMyProfile(user.getUsername());
    }

    @Operation(summary = "Update Login User Details")
    @PutMapping("/profile")
    public Optional<UserDto> update(@RequestBody UserDto userDto,
                                    @CurrentUser AuthenticatedUser user) {
        return userService.updateMyProfile(user.getUsername(), userDto);
    }

    @Operation(summary = "Upload Profile Pic")
    @PostMapping("/profile-upload")
    public void upload(@RequestParam("file") MultipartFile file,
                       @CurrentUser AuthenticatedUser authenticatedUser) throws IOException {
        userService.uploadProfilePic(authenticatedUser.getUsername(), file);
    }

    @Operation(summary = "Update Login User Password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordPayload payload,
                               @CurrentUser AuthenticatedUser user) {
        userService.changeUserPassword(user.getUsername(), payload);
    }
}
