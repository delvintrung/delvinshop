    package com.example.delvin.entity;
    import jakarta.persistence.*;
    import lombok.*;

    import java.time.Instant;
    import java.util.ArrayList;
    import java.util.List;

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
        @Column(nullable = false, updatable = false)
        private Instant createdAt;

        @Column(nullable = false)
        private Instant updatedAt;

        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private UserWallet wallet;

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
        private List<HistoryBuyLicense> historyBuyLicenses = new ArrayList<>();

        @PrePersist
        protected void onCreate() {
            Instant now = Instant.now();
            this.createdAt = now;
            this.updatedAt = now;
        }

        @PreUpdate
        protected void onUpdate() {
            this.updatedAt = Instant.now();
        }
    }
