package org.library.api.library_control_system.service;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import org.library.api.library_control_system.AuthorApi;
import org.library.invoker.library_control_system.ApiClient;
import org.library.model.library_control_system.AuthorCreateDTO;
import org.library.model.library_control_system.AuthorDTO;
import org.library.model.library_control_system.AuthorUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorApiService {

    private final AuthorApi authorApi = ApiClient.api(ApiClient.Config.apiConfig()).author();

    @Step("[POST /authors] Создать автора с именем '{firstName}' и фамилией '{lastName}'")
    public AuthorDTO createAuthor(String firstName, String lastName) {
        AuthorCreateDTO body = new AuthorCreateDTO().firstName(firstName).lastName(lastName);

        return authorApi.create1().body(body)
                .execute(r -> r.as(AuthorDTO.class));
    }

    @Step("[GET /authors/:id] Получить автора с id = {id}")
    public AuthorDTO getAuthorById(Long id) {
        return authorApi.show1().idPath(id)
                .execute(r -> r.as(AuthorDTO.class));
    }

    @Step("[GET /authors] Получить всех авторов")
    public List<AuthorDTO> getAuthors() {
        return authorApi.index1()
                .execute(r -> r.as(new TypeRef<>() { }));
    }

    @Step("[PUT /authors/:id] Изменить имя и фамилию автора с id = {id} на имя '{firstName}' и фамилию '{lastName}'")
    public AuthorDTO updateAuthorFirstAndLastName(Long id, String firstName, String lastName) {
        AuthorUpdateDTO body = new AuthorUpdateDTO().firstName(firstName).lastName(lastName);

        return updateAuthor(id, body);
    }

    @Step("[PUT /authors/:id] Изменить имя автора с id = {id} на имя '{firstName}'")
    public AuthorDTO updateAuthorFirstName(Long id, String firstName) {
        AuthorUpdateDTO body = new AuthorUpdateDTO().firstName(firstName);

        return updateAuthor(id, body);
    }

    @Step("[PUT /authors/:id] Изменить фамилию автора с id = {id} на фамилию '{lastName}'")
    public AuthorDTO updateAuthorLastName(Long id, String lastName) {
        AuthorUpdateDTO body = new AuthorUpdateDTO().lastName(lastName);

        return updateAuthor(id, body);
    }

    @Step("[DELETE /authors/:id] Удалить автора с id = {id}")
    public void deleteAuthor(Long id) {
        authorApi.delete1().idPath(id)
                .execute(r -> r);
    }

    private AuthorDTO updateAuthor(Long id, AuthorUpdateDTO body) {
        return authorApi.update1().idPath(id).body(body)
                .execute(r -> r.as(AuthorDTO.class));
    }
}
