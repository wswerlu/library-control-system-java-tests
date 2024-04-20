package org.library.databases.library_control_system.service;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.library.databases.library_control_system.entity.Author;
import org.library.databases.library_control_system.repository.AuthorRepository;
import org.library.exceptions.LibraryControlSystemException;
import org.library.utils.AllureLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorDbService {

    @Autowired
    private AuthorRepository authorRepository;

    @Step("Добавить автора в таблицу 'authors'")
    public void saveAuthor(Author author) {
        authorRepository.save(author);
        AllureLogging.attachText("В таблицу добавлен автор " + author);
    }

    @Step("Найти в таблице 'authors' автора с id '{0}'")
    @SneakyThrows
    public Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new LibraryControlSystemException("Author not found"));
    }

    @Step("Добавить авторов в таблицу 'authors'")
    public void saveAuthors(List<Author> authors) {
        authors.forEach(this::saveAuthor);
    }

    @Step("Удалить всех авторов из таблицы 'authors'")
    public void deleteAllAuthors() {
        authorRepository.deleteAll();
    }

    @Step("Найти всех авторов из таблицы 'authors'")
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }
}
