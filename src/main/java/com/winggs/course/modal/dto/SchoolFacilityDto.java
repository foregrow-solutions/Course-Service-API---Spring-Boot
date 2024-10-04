package com.winggs.course.modal.dto;

import com.winggs.course.modal.entity.Facility;
import com.winggs.course.modal.enums.Gender;
import com.winggs.course.modal.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolFacilityDto {
    int id;
    String schoolId;
    String firstName;
    String lastName;
    String email;
    String mobile;
    Gender gender;
    Double exp;
    String lastCompanyName;
    String licenseNumber;
    String address;

    public static SchoolFacilityDto of(Facility facility) {
        SchoolFacilityDto output = new SchoolFacilityDto();
        output.setId(facility.getId());
        output.setFirstName(facility.getUser().getFirstName());
        output.setLastName(facility.getUser().getLastName());
        output.setEmail(facility.getUser().getEmail());
        output.setMobile(facility.getUser().getMobile());
        output.setLicenseNumber(facility.getLicenseNumber());
        output.setGender(facility.getGender());
        output.setExp(facility.getExperience());
        output.setLastCompanyName(facility.getLastCompanyName());
        output.setAddress(facility.getAddress());
        return output;
    }
}
