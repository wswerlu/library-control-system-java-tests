package org.library.library_control_system.extentions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.library.databases.library_control_system.service.BookDbService;
import org.library.extentions.DefaultExtension;

public class DeleteAllFromBookTableExtension implements DefaultExtension, AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        BookDbService bookDbService = getBean(extensionContext, BookDbService.class);

        bookDbService.deleteAllBooks();
    }
}
