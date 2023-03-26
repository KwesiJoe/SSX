package io.staxex.api.DTO;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CreateWalletDTO {
    private String currency;
    private BigDecimal amount;
}
