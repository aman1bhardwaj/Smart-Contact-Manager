package com.scm.scm20.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
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
public class User implements UserDetails{

    // Reason for implementing UserDetails expalined in Read_Me. 

    @Id
    private String userId;

    @Column(name = "username", nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;

    @Getter(value=AccessLevel.NONE)
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
    private Boolean enabled = true;
    @Builder.Default
    private Boolean emailVerified = false;
    @Builder.Default
    private Boolean phoneNumberVerified = false;

    // Self , google , facebook
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Providers provider = Providers.SELF;
    private String providerId;

    // add more fields if needed
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();


    // Methods are added because of the implementation done above

    @ElementCollection(fetch = FetchType.EAGER)
    List<String> roleList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            //list of roles
            //Collection of simpleGrantedAuthority {role (ADMIN , USER)}

        Collection<SimpleGrantedAuthority> listRole = roleList
        .stream()
        .map(role -> new SimpleGrantedAuthority(role))
        .collect(Collectors.toList());
        
       return listRole;
    }

    @Override
    public String getPassword() {
       return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
