package org.library.library_control_system.author.extentions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.library.databases.library_control_system.service.AuthorDbService;
import org.library.extentions.DefaultExtension;

public class DeleteAllFromAuthorTableExtension implements DefaultExtension, AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        AuthorDbService authorDbService = getBean(extensionContext, AuthorDbService.class);

        authorDbService.deleteAllAuthors();
    }
}
