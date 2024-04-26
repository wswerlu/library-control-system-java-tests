package org.library.library_control_system;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.api.library_control_system.service.BookApiService;
import org.library.databases.library_control_system.entity.Author;
import org.library.databases.library_control_system.entity.Book;
import org.library.databases.library_control_system.service.BookDbService;
import org.library.library_control_system.extentions.DeleteAllFromBookTableExtension;
import org.library.model.library_control_system.BookDTO;
import org.library.utils.AllureLogging;
import org.library.utils.FakeTestData;
import org.library.utils.api.BookUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("library-control-system")
@Epic("library-control-system")
@Feature("book")
@ExtendWith(DeleteAllFromBookTableExtension.class)
@Tag("book")
@SpringBootTest()
class BookTests extends LibraryControlSystemBaseTests {

    @Autowired
    private BookApiService bookApiService;

    @Autowired
    private BookDbService bookDbService;

    private static final int BOOKS_COUNT = 5;

    @Test
    @DisplayName("[POST /books] Создание книги")
    void createBookTest() {
        Author author = createAndSaveAuthor();
        Long expectedAuthorId = author.getId();
        String expectedTitle = FakeTestData.createBook(expectedAuthorId).getTitle();

        Long bookId = bookApiService.createBook(expectedTitle, expectedAuthorId).getId();
        Book actualBook = bookDbService.findBookById(bookId);

        assertAll(
                () -> assertThat(actualBook.getTitle(),
                        AllureLogging.logMatcherWithText("title в таблице 'books' соответсвует "
                                + "данным из запроса", equalTo(expectedTitle))),
                () -> assertThat(actualBook.getAuthorId(),
                        AllureLogging.logMatcherWithText("authorId в таблице 'books' соответсвует "
                                + "данным из запроса", equalTo(expectedAuthorId)))
        );
    }

    @Test
    @DisplayName("[GET /books/:id] Получение книги по id")
    void findBookByIdTest() {
        Author expectedAuthor = createAndSaveAuthor();
        Long expectedAuthorId = expectedAuthor.getId();
        Book expectedBook = createAndSaveBook(expectedAuthorId);

        BookDTO actualBook = bookApiService.getBookById(expectedBook.getId());

        assertAll(
                () -> assertThat(actualBook.getTitle(),
                        AllureLogging.logMatcherWithText("title соответсвует данным из таблицы 'books'",
                                equalTo(expectedBook.getTitle()))),
                () -> assertThat(actualBook.getAuthorId(),
                        AllureLogging.logMatcherWithText("authorId соответсвует данным из таблицы 'books'",
                                equalTo(expectedAuthorId))),
                () -> assertThat(actualBook.getAuthorFirstName(),
                        AllureLogging.logMatcherWithText("authorFirstName соответсвует данным из таблицы "
                                        + "'authors'", equalTo(expectedAuthor.getFirstName()))),
                () -> assertThat(actualBook.getAuthorLastName(),
                        AllureLogging.logMatcherWithText("authorLastName соответсвует данным из таблицы 'authors'",
                                equalTo(expectedAuthor.getLastName())))
        );
    }

    @Test
    @DisplayName("[GET /books] Получение всех книг")
    void findAllBooksTest() {
        Author expectedAuthor = createAndSaveAuthor();
        List<Book> expectedBooks = FakeTestData.createBooks(expectedAuthor.getId(), BOOKS_COUNT);
        bookDbService.saveBooks(expectedBooks);

        List<BookDTO> booksFromApi = bookApiService.getBooks();
        List<Book> actualBooks = BookUtils.getBookList(booksFromApi);

        assertAll(
                () -> assertThat(actualBooks.size(),
                        AllureLogging.logMatcherWithText("Размер списка полученных книг соответствует "
                                + "количеству книг, добавленных в таблицу 'books'", equalTo(BOOKS_COUNT))),
                () -> actualBooks.forEach(
                        actualBook -> assertThat(actualBook,
                                AllureLogging.logMatcherWithText("Книга из списка с названием '"
                                                + actualBook.getTitle() + "' и автором '" + actualBook.getAuthorId()
                                                + "' есть в таблице 'books'", in(expectedBooks)))
                )

        );
    }

    @Test
    @DisplayName("[PUT /books/:id] Обновление названия и автора книги")
    void updateBookTitleAndAuthorTest() {
        Author author = createAndSaveAuthor();
        Book book = createAndSaveBook(author.getId());
        Long bookId = book.getId();

        Author expectedAuthor = createAndSaveAuthor();
        Long expectedAuthorId = expectedAuthor.getId();
        String expectedTitle = FakeTestData.createBook(expectedAuthorId).getTitle();

        bookApiService.updateBookTitleAndAuthor(bookId, expectedTitle, expectedAuthorId);
        Book actualBook = bookDbService.findBookById(bookId);

        assertAll(
                () -> assertThat(actualBook.getTitle(),
                        AllureLogging.logMatcherWithText("title в таблице 'books' соответсвует "
                                + "данным из запроса", equalTo(expectedTitle))),
                () -> assertThat(actualBook.getAuthorId(),
                        AllureLogging.logMatcherWithText("authorId в таблице 'books' соответсвует "
                                + "данным из запроса", equalTo(expectedAuthorId)))
        );
    }

    @Test
    @DisplayName("[PUT /books/:id] Обновление названия книги")
    @Disabled("Проблема с пустым authorId при обновлении книги")
    void updateBookTitle() {
        Author author = createAndSaveAuthor();
        Long expectedAuthorId = author.getId();
        Book book = createAndSaveBook(expectedAuthorId);
        Long bookId = book.getId();

        String expectedTitle = FakeTestData.createBook(expectedAuthorId).getTitle();

        bookApiService.updateBookTitle(bookId, expectedTitle);
        Book actualBook = bookDbService.findBookById(bookId);

        assertAll(
                () -> assertThat(actualBook.getTitle(),
                        AllureLogging.logMatcherWithText("title в таблице 'books' соответсвует "
                                + "данным из запроса", equalTo(expectedTitle))),
                () -> assertThat(actualBook.getAuthorId(),
                        AllureLogging.logMatcherWithText("authorId не изменился", equalTo(expectedAuthorId)))
        );
    }

    @Test
    @DisplayName("[PUT /books/:id] Обновление автора книги")
    void updateBookAuthorTest() {
        Author author = createAndSaveAuthor();
        Book book = createAndSaveBook(author.getId());
        Long bookId = book.getId();
        String expectedTitle = book.getTitle();

        Author expectedAuthor = FakeTestData.createAuthor();
        authorDbService.saveAuthor(expectedAuthor);
        Long expectedAuthorId = expectedAuthor.getId();

        bookApiService.updateBookAuthor(bookId, expectedAuthorId);
        Book actualBook = bookDbService.findBookById(bookId);

        assertAll(
                () -> assertThat(actualBook.getTitle(),
                        AllureLogging.logMatcherWithText("title не изменился", equalTo(expectedTitle))),
                () -> assertThat(actualBook.getAuthorId(),
                        AllureLogging.logMatcherWithText("authorId в таблице 'books' соответсвует "
                                + "данным из запроса", equalTo(expectedAuthorId)))
        );
    }

    @Test
    @DisplayName("[DELETE /books/:id] Удаление книги с указанным id")
    void deleteBookWithIdTest() {
        Author author = createAndSaveAuthor();
        Book book = createAndSaveBook(author.getId());
        Long bookId = book.getId();

        bookApiService.deleteBook(bookId);
        List<Book> booksFromDb = bookDbService.findAllBooks();

        assertThat(booksFromDb,
                AllureLogging.logMatcherWithText("В таблице 'books' нет книги с id " + bookId,
                        not(hasItem(book))));
    }

    private Book createAndSaveBook(Long authorId) {
        Book expectedBook = FakeTestData.createBook(authorId);
        bookDbService.saveBook(expectedBook);
        return expectedBook;
    }
}
