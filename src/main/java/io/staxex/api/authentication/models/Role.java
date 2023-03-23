package io.staxex.api.authentication.models;

import io.staxex.api.authentication.enums.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @SequenceGenerator(
            name = "roleSequence",
            sequenceName = "roleSequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "roleSequence"
    )
    private Long id;
    @Enumerated(EnumType.STRING)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}

