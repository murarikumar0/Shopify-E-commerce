package com.ms.Ecomm.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDTO {

    private String name;

    private String email;

    private String password;
}
