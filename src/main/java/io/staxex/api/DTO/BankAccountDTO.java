package io.staxex.api.DTO;

import lombok.Getter;

@Getter
public class BankAccountDTO {
    private String identifier;
    private String bankName;
    private String accountName;
    private String accountNumber;

    public BankAccountDTO(String bankName, String accountName, String accountNumber) {
        this.bankName = bankName;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
    }

}

