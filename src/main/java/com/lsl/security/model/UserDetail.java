package com.lsl.security.model;

import lombok.Data;

@Data
public class UserDetail {
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}
