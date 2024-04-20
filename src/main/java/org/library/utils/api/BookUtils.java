package org.library.utils.api;

import org.library.databases.library_control_system.entity.Book;
import org.library.model.library_control_system.BookDTO;

import java.util.List;

public class BookUtils {

    private BookUtils() {
    }

    public static List<Book> getBookList(List<BookDTO> bookDTOList) {
        return bookDTOList.stream()
                .map(bookDTO -> new Book().setTitle(bookDTO.getTitle()).setAuthorId(bookDTO.getAuthorId()))
                .toList();
    }
}
