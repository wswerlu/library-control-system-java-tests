package org.library.databases.library_control_system.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "books")
@Getter
@Setter
@EqualsAndHashCode(of = {"authorId", "title"})
@ToString
@Accessors(chain = true)
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "AUTHOR_ID")
    private Long authorId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CREATED_AT")
    private LocalDate createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;
}
