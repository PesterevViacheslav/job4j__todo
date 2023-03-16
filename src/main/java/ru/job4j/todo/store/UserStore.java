package ru.job4j.todo.store;
import org.hibernate.SessionFactory;
import ru.job4j.todo.model.User;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
/**
 * Class UserStore - Хранилище пользователей в БД postgres.
 * Решение задач уровня Middle. Части 3.3. Hibernate.
 * Создать TODO list [#3786]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 01.10.2022
 * @version 1
 */
@Repository
@AllArgsConstructor
public class UserStore implements Store {
    private final SessionFactory sf;

    public Optional<User> findUserByEmailAndPassword(String name, String password) {
        return this.tx(
                session -> session.createQuery("from User where user_name = :fname and password = :fpassword")
                        .setParameter("fname", name)
                        .setParameter("fpassword", password)
                        .setMaxResults(1).uniqueResultOptional(), sf
        );
    }

    public User createUser(User user) {
        return this.tx(
                session -> {
                    session.save(user);
                    return user;
                }, sf
        );
    }
}
