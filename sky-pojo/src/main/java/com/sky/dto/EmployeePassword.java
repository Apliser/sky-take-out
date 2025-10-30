package com.sky.dto;


import lombok.Data;
@Data
public class EmployeePassword {
    private Long id;
    private String oldPassword;
    private String newPassword;
}
