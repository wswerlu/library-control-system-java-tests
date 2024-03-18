package org.library.databases.library_control_system;

import org.library.config.database.LibraryControlSystemJpaConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({LibraryControlSystemJpaConfig.class})
public class LibraryControlSystemDataBaseAutoConfiguration {
}
