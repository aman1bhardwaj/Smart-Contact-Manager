package com.scm.scm20.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private String userId;

    @Column(name = "username", nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @Column(length = 5000, columnDefinition = "TEXT")
    private String about;
    @Column(length = 10000)
    @Lob
    private String profilePic;
    private String gender;
    private String phoneNumber;

    // Information
    @Builder.Default
    private Boolean isenabled = false;
    @Builder.Default
    private Boolean isemailVerified = false;
    @Builder.Default
    private Boolean isphoneNumberVerified = false;

    // Self , google , facebook
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Providers provider = Providers.SELF;
    private String providerId;

    // add more fields if needed
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();
}
