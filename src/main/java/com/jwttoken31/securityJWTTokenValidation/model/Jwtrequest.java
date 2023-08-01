package com.jwttoken31.securityJWTTokenValidation.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Jwtrequest {

    private String userName;

    private String password;

}
