package io.staxex.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankAccount {
    @Id
    @SequenceGenerator(
            name = "bankAccountSequence",
            sequenceName = "bankAccountSequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bankAccountSequence"
    )
    private Long id;

    @ManyToOne
    private Trader trader;
    private String bankName;
    private String accountName;
    private String accountNumber;

}
