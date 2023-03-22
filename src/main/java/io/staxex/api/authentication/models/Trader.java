package io.staxex.api.authentication.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class Trader {
    @Id
    @SequenceGenerator(
            name = "traderSequence",
            sequenceName = "traderSequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "traderSequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
