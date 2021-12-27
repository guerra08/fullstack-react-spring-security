package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "password_tokens")
public class PasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "is_used")
    private boolean isUsed = false;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_user")
    private User user;

    @Column(name = "expiration_date")
    @NonNull
    private Instant expirationDate;

    /**
     * Checks if a PasswordToken is valid (not used and not expired)
     * @return boolean
     */
    public boolean isTokenValid(){
        return !this.isUsed && this.expirationDate.isAfter(Instant.now());
    }

}
