package com.nal.pdfcvbuilder.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String password;

    @Column(unique = true)
    private String email;

    private String nationality;
    private String location;
    private String summary;
    private String title;
    private String profession;

    @Column(name = "mobile_phone_number")
    private String mobile;

    @Column(name = "image_path")
    private String image;

    @Column(name = "relevant_experience_years")
    private int years;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Resume resume;
}

