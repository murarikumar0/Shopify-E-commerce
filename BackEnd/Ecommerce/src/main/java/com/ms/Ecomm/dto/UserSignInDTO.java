package com.ms.Ecomm.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInDTO {

    private String email;

    private String password;


}
