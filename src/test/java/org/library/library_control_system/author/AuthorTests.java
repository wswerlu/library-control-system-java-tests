package org.library.library_control_system.author;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.library.api.library_control_system.service.AuthorApiService;
import org.library.databases.library_control_system.entity.Author;
import org.library.model.library_control_system.AuthorDTO;
import org.library.utils.AllureLogging;
import org.library.utils.FakeTestData;
import org.library.utils.api.AuthorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("library-control-system")
@Epic("library-control-system")
@Feature("author")
@SpringBootTest()
class AuthorTests extends LibraryControlSystemBaseTests {

    @Autowired
    private AuthorApiService authorApiService;

    private static final int AUTHORS_COUNT = 5;

    @Test
    @DisplayName("[POST /authors] Создание автора")
    void createAuthorTest() {
        Author expectedAuthor = FakeTestData.createAuthor();
        String expectedFirstName = expectedAuthor.getFirstName();
        String expectedLastName = expectedAuthor.getLastName();

        Long authorId = authorApiService.createAuthor(expectedFirstName, expectedLastName).getId();

        Author actualAuthor = authorDbService.findAuthorById(authorId);

        assertAll(
                () -> assertThat(actualAuthor.getFirstName(),
                        AllureLogging.logMatcherWithText("firstName в таблице 'authors' соответсвует "
                                + "данным из запроса", equalTo(expectedFirstName))),
                () -> assertThat(actualAuthor.getLastName(),
                        AllureLogging.logMatcherWithText("lastName в таблице 'authors' соответсвует "
                                + "данным из запроса", equalTo(expectedLastName)))
        );
    }

    @Test
    @DisplayName("[GET /authors/:id] Получение автора по id")
    void findAuthorByIdTest() {
        Author expectedAuthor = createAndSaveAuthor();

        AuthorDTO actualAuthor = authorApiService.getAuthorById(expectedAuthor.getId());

        assertAll(
                () -> assertThat(actualAuthor.getFirstName(),
                        AllureLogging.logMatcherWithText("firstName соответсвует данным из таблицы 'authors'",
                                equalTo(expectedAuthor.getFirstName()))),
                () -> assertThat(actualAuthor.getLastName(),
                        AllureLogging.logMatcherWithText("lastName соответсвует данным из таблицы 'authors'",
                                equalTo(expectedAuthor.getLastName())))
        );
    }

    @Test
    @DisplayName("[GET /authors] Получение всех авторов")
    void findAllAuthorsTest() {
        List<Author> expectedAuthors = FakeTestData.createAuthors(AUTHORS_COUNT);
        authorDbService.saveAuthors(expectedAuthors);

        List<AuthorDTO> authorsFromApi = authorApiService.getAuthors();
        List<Author> actualAuthors = AuthorUtils.getAuthorList(authorsFromApi);

        assertAll(
                () -> assertThat(actualAuthors.size(),
                        AllureLogging.logMatcherWithText("Размер списка полученных авторов соответствует "
                                + "количеству авторов, добавленных в таблицу 'authors'", equalTo(AUTHORS_COUNT))),
                () -> actualAuthors.forEach(
                        actualAuthor -> assertThat(actualAuthor,
                                AllureLogging.logMatcherWithText("Автор из списка с именем '"
                                                + actualAuthor.getFirstName() + "' и фамилией '"
                                                + actualAuthor.getLastName() + "' есть в таблице 'authors'",
                                        in(expectedAuthors)))
                )

        );
    }

    @Test
    @DisplayName("[PUT /authors/:id] Обновление имени и фамилии автора")
    void updateFirstAndLastNameTest() {
        Author author = createAndSaveAuthor();
        Long authorId = author.getId();

        Author expectedAuthor = FakeTestData.createAuthor();
        String expectedFirstName = expectedAuthor.getFirstName();
        String expectedLastName = expectedAuthor.getLastName();

        authorApiService.updateAuthorFirstAndLastName(authorId, expectedFirstName, expectedLastName);
        Author actualAuthor = authorDbService.findAuthorById(authorId);

        assertAll(
                () -> assertThat(actualAuthor.getFirstName(),
                        AllureLogging.logMatcherWithText("firstName в таблице 'authors' соответсвует "
                                + "данным из запроса", equalTo(expectedFirstName))),
                () -> assertThat(actualAuthor.getLastName(),
                        AllureLogging.logMatcherWithText("lastName в таблице 'authors' соответсвует "
                                + "данным из запроса", equalTo(expectedLastName)))
        );
    }

    @Test
    @DisplayName("[PUT /authors/:id] Обновление имени автора")
    void updateFirstNameTest() {
        Author author = createAndSaveAuthor();
        Long authorId = author.getId();
        String expectedLastName = author.getLastName();

        String expectedFirstName = FakeTestData.createAuthor().getFirstName();

        authorApiService.updateAuthorFirstName(authorId, expectedFirstName);
        Author actualAuthor = authorDbService.findAuthorById(authorId);

        assertAll(
                () -> assertThat(actualAuthor.getFirstName(),
                        AllureLogging.logMatcherWithText("firstName в таблице 'authors' соответсвует "
                                + "данным из запроса", equalTo(expectedFirstName))),
                () -> assertThat(actualAuthor.getLastName(),
                        AllureLogging.logMatcherWithText("lastName не изменилось", equalTo(expectedLastName)))
        );
    }

    @Test
    @DisplayName("[PUT /authors/:id] Обновление фамилии автора")
    void updateLastNameTest() {
        Author author = createAndSaveAuthor();
        Long authorId = author.getId();
        String expectedFirstName = author.getFirstName();

        String expectedLastName = FakeTestData.createAuthor().getLastName();

        authorApiService.updateAuthorLastName(authorId, expectedLastName);
        Author actualAuthor = authorDbService.findAuthorById(authorId);

        assertAll(
                () -> assertThat(actualAuthor.getFirstName(),
                        AllureLogging.logMatcherWithText("firstName не изменилось", equalTo(expectedFirstName))),
                () -> assertThat(actualAuthor.getLastName(),
                        AllureLogging.logMatcherWithText("lastName в таблице 'authors' соответсвует "
                                + "данным из запроса", equalTo(expectedLastName)))
        );
    }

    @Test
    @DisplayName("[DELETE /authors/:id] Удаление автора с указанным id")
    void deleteAuthorWithIdTest() {
        Author author = createAndSaveAuthor();
        Long authorId = author.getId();

        authorApiService.deleteAuthor(authorId);
        List<Author> authorsFromDb = authorDbService.findAllAuthors();

        assertThat(authorsFromDb,
                AllureLogging.logMatcherWithText("В таблице 'authors' нет автора с id " + authorId,
                        not(hasItem(author))));
    }
}
