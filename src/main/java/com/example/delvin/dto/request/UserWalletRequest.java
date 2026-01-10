package com.example.delvin.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Setter
@AllArgsConstructor
public class UserWalletRequest {
    private BigDecimal balance;
    private String currency;
}
