package com.example.delvin.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
public class UserWalletRequest {
    private Double balance;
    private String currency;
}
