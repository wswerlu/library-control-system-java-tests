package org.library.databases.library_control_system.service;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.library.databases.library_control_system.entity.Book;
import org.library.databases.library_control_system.repository.BookRepository;
import org.library.exceptions.LibraryControlSystemException;
import org.library.utils.AllureLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDbService {

    @Autowired
    private BookRepository bookRepository;

    @Step("Удалить все книги из таблицы 'books'")
    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

    @Step("Добавить книгу в таблицу 'books'")
    public void saveBook(Book book) {
        bookRepository.save(book);
        AllureLogging.attachText("В таблицу добавлена книга " + book);
    }

    @Step("Найти в таблице 'books' книгу с id {id}")
    @SneakyThrows
    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new LibraryControlSystemException("Book not found"));
    }

    @Step("Добавить книги в таблицу 'books'")
    public void saveBooks(List<Book> books) {
        books.forEach(this::saveBook);
    }

    @Step("Найти все книги из таблицы 'books'")
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
}
