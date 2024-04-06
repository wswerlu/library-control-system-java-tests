package org.library.databases.library_control_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@Setter
@EqualsAndHashCode(of = {"authorId", "title"})
@ToString
public class Book {
    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "AUTHOR_ID")
    private long authorId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CREATED_AT")
    private LocalDate createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;
}
