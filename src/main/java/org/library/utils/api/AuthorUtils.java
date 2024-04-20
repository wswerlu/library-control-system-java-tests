package org.library.utils.api;

import org.library.databases.library_control_system.entity.Author;
import org.library.model.library_control_system.AuthorDTO;

import java.util.List;

public class AuthorUtils {

    private AuthorUtils() {
    }

    public static List<Author> getAuthorList(List<AuthorDTO> authorDTOList) {
        return authorDTOList.stream()
                .map(author -> new Author().setFirstName(author.getFirstName()).setLastName(author.getLastName()))
                .toList();
    }
}
