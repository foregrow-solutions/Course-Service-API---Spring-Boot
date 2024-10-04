package com.winggs.course.modal.dto;

import com.winggs.course.modal.entity.Student;
import com.winggs.course.modal.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StudentDto {
    String id;
    String permitNumber;
    String firstName;
    String lastName;
    String email;
    String mobile;
    Gender gender;
    Double fee;
    Date dob;
    String address;

    public static StudentDto of(Student student) {
        StudentDto output = new StudentDto();
        output.setId(student.getId());
        output.setPermitNumber(student.getPermitNo());
        output.setFirstName(student.getUser().getFirstName());
        output.setLastName(student.getUser().getLastName());
        output.setEmail(student.getUser().getEmail());
        output.setMobile(student.getUser().getMobile());
        output.setGender(student.getGender());
        output.setDob(student.getDob());
        output.setFee(student.getFee());
        output.setAddress(student.getAddress());
        return output;
    }
}
