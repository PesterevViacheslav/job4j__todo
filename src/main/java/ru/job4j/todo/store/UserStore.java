package ru.job4j.todo.store;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.todo.model.User;
import java.util.function.Function;
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
public class UserStore {
    private final SessionFactory sf;
    /**
     * Method tx. Применение шаблона проектирования wrapper.
     * @param command
     * @param <T>
     * @return T
     */
    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public User findUser(String name, String pwd) {
        if (name != null) {
            return (User) this.tx(
                    session -> session.createQuery("from ru.job4j.todo.model.User where user_name = :fname and password = :fpwd")
                            .setParameter("fname", name)
                            .setParameter("fpwd", pwd)
                            .setMaxResults(1).uniqueResult()
            );
        } else {
            return (User) this.tx(
                    session -> session.createQuery("from ru.job4j.todo.model.User").setMaxResults(1).uniqueResult()
            );
        }
    }

    public User createUser(User user) {
        return this.tx(
                session -> {
                    session.save(user);
                    return user;
                }
        );
    }
}
