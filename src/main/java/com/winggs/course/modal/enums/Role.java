package com.winggs.course.modal.enums;

public enum Role {
    ADMIN("Admin Can Manage"), SCHOOL_ADMIN("For School Admin"),
    SCHOOL_FACILITY("Manage School Student"), STUDENT("Student");

    String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
