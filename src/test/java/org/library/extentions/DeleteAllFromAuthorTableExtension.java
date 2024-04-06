package org.library.extentions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.library.databases.library_control_system.repository.AuthorRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DeleteAllFromAuthorTableExtension implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        AuthorRepository authorRepository = SpringExtension
                .getApplicationContext(extensionContext).getBean(AuthorRepository.class);

        authorRepository.deleteAll();
    }
}
