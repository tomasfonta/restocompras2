package com.tf.restocompras.config.jwt;


import lombok.Data;

@Data
public class UserAuthenticationRequest {

    private String name;
    private String password;

}
