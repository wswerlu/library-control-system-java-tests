package org.library.library_control_system.author;

import org.junit.jupiter.api.extension.ExtendWith;
import org.library.api.library_control_system.LibraryControlSystemApiAutoConfiguration;
import org.library.databases.library_control_system.LibraryControlSystemDbAutoConfiguration;
import org.library.databases.library_control_system.service.AuthorDbService;
import org.library.extentions.RestAssuredExtension;
import org.library.library_control_system.author.extentions.DeleteAllFromAuthorTableExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {LibraryControlSystemDbAutoConfiguration.class,
        LibraryControlSystemApiAutoConfiguration.class})
@ExtendWith({RestAssuredExtension.class, DeleteAllFromAuthorTableExtension.class})
class LibraryControlSystemBaseTests {

    protected LibraryControlSystemBaseTests() {
    }

    @Autowired
    protected AuthorDbService authorDbService;
}