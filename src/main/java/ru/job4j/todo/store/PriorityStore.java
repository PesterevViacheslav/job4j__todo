package ru.job4j.todo.store;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import java.util.List;
/**
 * Class PriorityStore - Хранилище приоритетов в БД postgres.
 * Решение задач уровня Middle. Части 3.3. Hibernate.
 * Создать TODO list [#3786]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 23.02.2023
 * @version 1
 */
@Repository
@AllArgsConstructor
public class PriorityStore implements Store {
    private final SessionFactory sf;
    /**
     * Method findAllPriorities. Получение списка приоритетов.
     * @return Приоритеты.
     */
    public List<Priority> findAllPriorities() {
        return this.tx(
                session -> {
                    return session.createQuery(" from Priority"
                                    + " order by 1")
                            .list();
                }, sf
        );
    }
}