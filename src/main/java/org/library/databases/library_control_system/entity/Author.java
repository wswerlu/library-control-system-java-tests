package org.library.databases.library_control_system.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "authors", uniqueConstraints = @UniqueConstraint(columnNames = {"firstName", "lastName"}))
@Getter
@Setter
@EqualsAndHashCode(of = {"firstName", "lastName"})
@ToString
@Accessors(chain = true)
public class Author {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "CREATED_AT")
    private LocalDate createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;
}
