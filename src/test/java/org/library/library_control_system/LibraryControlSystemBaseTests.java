package org.library.library_control_system;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.api.library_control_system.LibraryControlSystemApiAutoConfiguration;
import org.library.databases.library_control_system.LibraryControlSystemDbAutoConfiguration;
import org.library.databases.library_control_system.entity.Author;
import org.library.databases.library_control_system.service.AuthorDbService;
import org.library.extentions.RestAssuredExtension;
import org.library.library_control_system.extentions.DeleteAllFromAuthorTableExtension;
import org.library.utils.FakeTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {LibraryControlSystemDbAutoConfiguration.class,
        LibraryControlSystemApiAutoConfiguration.class})
@ExtendWith({RestAssuredExtension.class, DeleteAllFromAuthorTableExtension.class})
@Tag("library-control-system")
class LibraryControlSystemBaseTests {

    protected LibraryControlSystemBaseTests() {
    }

    @Autowired
    protected AuthorDbService authorDbService;

    protected Author createAndSaveAuthor() {
        Author author = FakeTestData.createAuthor();
        authorDbService.saveAuthor(author);
        return author;
    }
}
