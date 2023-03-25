package io.staxex.api.providers.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

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

    @JsonIgnore
    private String apiUrl;

    @JsonIgnore
    private String apiToken;

    public LiquidityProvider(String name, String website, String contactPerson, String email, String phoneNumber) {
        this.name = name;
        this.website = website;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
