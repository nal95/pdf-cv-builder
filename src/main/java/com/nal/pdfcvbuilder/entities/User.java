package com.nal.pdfcvbuilder.entities;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

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
    private String objectives;
    private String profession;

    @Column(name = "mobile_phone_number")
    private String mobile;
    private String image;

    @Column(name = "relevant_experience_years")
    private int years;

    @Column(columnDefinition = "json")
    @Type(JsonType.class)
    private CVData data;
}

