package com.winggs.course.modal.payload;


import com.winggs.course.modal.enums.Gender;
import com.winggs.course.validator.UniqueUsername;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

public record CreateUserPayload(String firstName, String lastName, String username, String mobile, Date dob,
                                String password, String permitNo, Gender gender) {
}
