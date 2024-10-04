package com.winggs.course.modal.dto;

import com.winggs.course.modal.entity.School;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolDto {
    String id;
    String name;
    String dunsNo;
    String fedTaxNo;
    String address;
    String mobile;
    String email;
    String website;
    Double fee;

    public static SchoolDto of(School school){
        SchoolDto output = new SchoolDto();
        output.setId(school.getId());
        output.setName(school.getName());
        output.setDunsNo(school.getDunsNo());
        output.setFedTaxNo(school.getFedTaxNo());
        output.setMobile(school.getUser().getMobile());
        output.setAddress(school.getAddress());
        output.setEmail(school.getUser().getEmail());
        output.setWebsite(school.getWebsite());
        output.setFee(school.getFee());
        return output;
    }
}
