package org.library.extentions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public interface DefaultExtension {

    default <T> T getBean(ExtensionContext context, Class<T> bean) {
        return SpringExtension.getApplicationContext(context).getBean(bean);
    }
}
