package org.library.databases.library_control_system.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "authors", uniqueConstraints = @UniqueConstraint(columnNames = {"firstName", "lastName"}))
@Getter
@Setter
@EqualsAndHashCode(of = {"firstName", "lastName"})
public class Author {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "CREATED_AT")
    private LocalDate createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;
}
