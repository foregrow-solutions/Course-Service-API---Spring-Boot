package com.winggs.course.controller;

import com.winggs.course.modal.common.ApplicationException;
import com.winggs.course.modal.common.ErrorCode;
import com.winggs.course.modal.dto.UserDto;
import com.winggs.course.modal.payload.CreateUserPayload;
import com.winggs.course.modal.payload.UserLoginPayload;
import com.winggs.course.security.TokenService;
import com.winggs.course.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Auth APIs", description = "API for manage User Auth Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final UserService service;
    private final TokenService tokenService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("auth/login");
        return vi;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("auth/register");
        return vi;
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public ModelAndView forgotPassword() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("auth/forgot-password");
        return vi;
    }

    @Operation(summary = "Register a new User")
    @PostMapping("auth/register")
    public UserDto register(@RequestBody CreateUserPayload payload) {
        return service.create(payload);
    }

    @Operation(summary = "User Login End-Point")
    @PostMapping("/auth/login")
    public ResponseEntity<?> token(@Valid @RequestBody UserLoginPayload request) {
        log.debug("Login request from user: {}", request.getUsername());
        var user = service.findByUsername(request.getUsername()).orElseThrow(() -> new ApplicationException(ErrorCode.BAD_CRED, "Username not found"));
        var success = service.validateCredentials(request.getPassword(), user.getPassword());
        if (success) {
            var token = tokenService.generate(String.valueOf(user.getId()), 365);
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("access_token", token);
            tokenMap.put("name", user.getFirstName());
            tokenMap.put("role", user.getRole());
            if (user.getSchool() != null) {
                tokenMap.put("school_id", user.getSchool().getId());
            }
            tokenMap.put("expires_in", 360000000);
            return ResponseEntity.ok(tokenMap);
        } else {
            throw new ApplicationException(ErrorCode.BAD_CRED, "Invalid credentials");
        }
    }

//    @Operation(summary = "Forgot Password")
//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordPayload payload){
//        service.forgotUserPassword(payload.username());
//        return ResponseEntity.ok().build();
//    }
//
//    @Operation(summary = "Verify OTP")
//    @PostMapping("/verify")
//    public String verifyOtp(String opt){
//        return service.checkPasswordRestOtp(opt);
//    }

}
