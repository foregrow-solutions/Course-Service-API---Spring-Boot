package com.winggs.course.modal.dto;

import com.winggs.course.modal.enums.Role;
import com.winggs.course.modal.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserDto {
    String id;
    String firstName;
    String lastName;
    String email;
    String mobile;
    String imageUrl;
    String schoolId;
    Role role;
    Instant createdDate;
    Instant modifiedDate;

    public static UserDto of(User user) {
        UserDto output = new UserDto();
        output.setId(user.getId());
        output.setFirstName(user.getFirstName());
        output.setLastName(user.getLastName());
        output.setEmail(user.getEmail());
        output.setMobile(user.getMobile());
        output.setImageUrl(user.getImageUrl());
        if (user.getSchool()!=null){
            output.setSchoolId(user.getSchool().getId());
        }
        output.setRole(user.getRole());
        output.setCreatedDate(user.getCreatedDate());
        output.setModifiedDate(user.getModifiedDate());
        return output;
    }
}
