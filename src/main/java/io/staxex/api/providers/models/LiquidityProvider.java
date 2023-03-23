package io.staxex.api.providers.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LiquidityProvider {
    @SequenceGenerator(
            name = "liquidityProviderSequence",
            sequenceName = "liquidityProviderSequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "liquidityProviderSequence"
    )
    @Id
    private Long id;
    private String name;
    private String website;
    private String contactPerson;
    private String email;
    private String phoneNumber;

}
