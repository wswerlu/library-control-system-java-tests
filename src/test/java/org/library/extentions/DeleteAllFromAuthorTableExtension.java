package org.library.extentions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.library.databases.library_control_system.service.AuthorDbService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DeleteAllFromAuthorTableExtension implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        AuthorDbService authorDbService = SpringExtension
                .getApplicationContext(extensionContext).getBean(AuthorDbService.class);

        authorDbService.deleteAllAuthors();
    }
}
