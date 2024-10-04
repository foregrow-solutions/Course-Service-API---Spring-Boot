package com.winggs.course.modal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboardDto {
    int totalStudents;
    int totalSchools;
    int totalFee;

    public AdminDashboardDto of(int totalStudents, int totalSchools, int totalFee) {
        AdminDashboardDto output = new AdminDashboardDto();
        output.totalStudents = totalStudents;
        output.setTotalStudents(totalStudents);
        output.setTotalSchools(totalSchools);
        output.setTotalFee(totalFee);
        return output;
    }


}
