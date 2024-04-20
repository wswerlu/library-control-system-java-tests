package org.library.api.library_control_system.service;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import org.library.api.library_control_system.BookApi;
import org.library.invoker.library_control_system.ApiClient;
import org.library.model.library_control_system.BookCreateDTO;
import org.library.model.library_control_system.BookDTO;
import org.library.model.library_control_system.BookUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookApiService {

    private final BookApi bookApi = ApiClient.api(ApiClient.Config.apiConfig()).book();

    @Step("[POST /books] Создать книгу '{title}'")
    public BookDTO createBook(String title, Long authorId) {
        BookCreateDTO body = new BookCreateDTO().authorId(authorId).title(title);

        return bookApi.create().body(body)
                .execute(r -> r.as(BookDTO.class));
    }

    @Step("[GET /books/:id] Получить книгу с id = {id}")
    public BookDTO getBookById(Long id) {
        return bookApi.show().idPath(id)
                .execute(r -> r.as(BookDTO.class));
    }

    @Step("[GET /books] Получить все книги")
    public List<BookDTO> getBooks() {
        return bookApi.index()
                .execute(r -> r.as(new TypeRef<>() { }));
    }

    @Step("[PUT /books/:id] Изменить заголовок и автора книги с id = {id} на заголовок '{title} и автора {authorId}'")
    public BookDTO updateBookTitleAndAuthor(Long id, String title, Long authorId) {
        BookUpdateDTO body = new BookUpdateDTO().authorId(authorId).title(title);

        return updateBook(id, body);
    }

    @Step("[PUT /books/:id] Изменить заголовок книги с id = {id} на заголовок '{title}")
    public BookDTO updateBookTitle(Long id, String title) {
        BookUpdateDTO body = new BookUpdateDTO().title(title);

        return updateBook(id, body);
    }

    @Step("[PUT /books/:id] Изменить автора книги с id = {id} на автора {authorId}'")
    public BookDTO updateBookAuthor(Long id, Long authorId) {
        BookUpdateDTO body = new BookUpdateDTO().authorId(authorId);

        return updateBook(id, body);
    }

    @Step("[DELETE /books/:id] Удалить книгу с id = {id}")
    public void deleteBook(Long id) {
        bookApi.delete().idPath(id)
                .execute(r -> r);
    }

    private BookDTO updateBook(Long id, BookUpdateDTO body) {
        return bookApi.update().idPath(id).body(body)
                .execute(r -> r.as(BookDTO.class));
    }
}
