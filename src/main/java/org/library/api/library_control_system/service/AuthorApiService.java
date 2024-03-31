package org.library.api.library_control_system.service;

import io.qameta.allure.Step;
import org.library.api.library_control_system.AuthorApi;
import org.library.invoker.library_control_system.ApiClient;
import org.library.model.library_control_system.AuthorCreateDTO;
import org.library.model.library_control_system.AuthorDTO;
import org.library.model.library_control_system.AuthorUpdateDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthorApiService {
    private final AuthorApi authorApi = ApiClient.api(ApiClient.Config.apiConfig()).author();

    @Step("[POST /authors] Создать автора с именем '{0}' и фамилией '{1}'")
    public AuthorDTO createAuthor(String firstName, String lastName) {
        AuthorCreateDTO body = new AuthorCreateDTO().firstName(firstName).lastName(lastName);

        return authorApi.create1().body(body)
                .execute(r -> r.as(AuthorDTO.class));
    }

    @Step("[GET /authors/:id] Получить автора с id = {0}")
    public AuthorDTO getAuthorById(long id) {
        return authorApi.show1().idPath(id)
                .execute(r -> r.as(AuthorDTO.class));
    }

    @Step("[GET /authors] Получить всех авторов")
    public AuthorDTO getAuthors() {
        return authorApi.index1()
                .execute(r -> r.as(AuthorDTO.class));
    }

    @Step("[PUT /authors/:id] Изменить имя и фамилию автора с id = {0} на имя '{1}' и фамилию '{2}'")
    public AuthorDTO updateAuthorFirstAndLastName(long id, String firstName, String lastName) {
        AuthorUpdateDTO body = new AuthorUpdateDTO().firstName(firstName).lastName(lastName);

        return authorApi.update1().idPath(id).body(body)
                .execute(r -> r.as(AuthorDTO.class));
    }

    @Step("[PUT /authors/:id] Изменить имя автора с id = {0} на имя '{1}'")
    public AuthorDTO updateAuthorFirstName(long id, String firstName) {
        AuthorUpdateDTO body = new AuthorUpdateDTO().firstName(firstName);

        return authorApi.update1().idPath(id).body(body)
                .execute(r -> r.as(AuthorDTO.class));
    }

    @Step("[PUT /authors/:id] Изменить фамилию автора с id = {0} на фамилию '{1}'")
    public AuthorDTO updateAuthorLastName(long id, String lastName) {
        AuthorUpdateDTO body = new AuthorUpdateDTO().lastName(lastName);

        return authorApi.update1().idPath(id).body(body)
                .execute(r -> r.as(AuthorDTO.class));
    }

    @Step("[DELETE /authors/:id] Удалить автора с id = {0}")
    public void deleteAuthor(long id) {
        authorApi.delete1().idPath(id)
                .execute(r -> r.as(AuthorDTO.class));
    }
}
