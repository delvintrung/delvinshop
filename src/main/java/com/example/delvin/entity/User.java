    package com.example.delvin.entity;
    import jakarta.persistence.*;
    import lombok.*;

    @Entity
    @Getter
    @Setter
    @Builder
    @Table(name="users")
    @NoArgsConstructor
    @AllArgsConstructor
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String fullName;
        private String email;
        private String password;
        private String provider;
        private String avatar;
        private String googleId;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "wallet_id", referencedColumnName = "id")
        private UserWallet wallet;
    }
