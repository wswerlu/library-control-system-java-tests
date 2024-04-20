package org.library.utils;

import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Select;
import org.library.databases.library_control_system.entity.Author;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

public class FakeTestData {

    private FakeTestData() {
    }

    private static final Faker FAKER = new Faker(Locale.ENGLISH);

    public static Author createAuthor() {
        return Instancio.of(Author.class)
                .ignore(Select.fields(field -> "id".equals(field.getName())))
                .supply(Select.field(Author::getFirstName), () -> FAKER.name().firstName())
                .supply(Select.field(Author::getLastName), () -> FAKER.name().lastName())
                .supply(Select.field(Author::getCreatedAt), () -> LocalDate.now())
                .supply(Select.field(Author::getUpdatedAt), () -> LocalDate.now())
                .create();
    }

    public static List<Author> createAuthors(int count) {
        return IntStream.range(0, count)
                .mapToObj(author -> createAuthor())
                .toList();
    }
}
