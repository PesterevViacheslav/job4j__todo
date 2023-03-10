package ru.job4j.todo.store;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.Optional;

/**
 * Class CategoryStore - Хранилище категорий в БД postgres.
 * Решение задач уровня Middle. Части 3.3. Hibernate.
 * 4. Категории в TODO List [#331991]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 10.03.2023
 * @version 1
 */
@Repository
@AllArgsConstructor
public class CategoryStore implements Store {
    private final SessionFactory sf;
    /**
     * Method findAllCategories. Получение списка категорий.
     * @return Приоритеты.
     */
    public List<Category> findAllCategories() {
        return this.tx(
                session -> {
                    return session.createQuery(" from Category"
                                    + " order by 1")
                            .list();
                }, sf
        );
    }
    /**
     * Method findById. Поиск по ID.
     * @param id ID категории.
     * @return Категория
     */
    public Optional<Category> findById(int id) {
        return this.tx(
                session -> {
                    return session.createQuery(" from Category i"
                                    + " where i.id = :category_id")
                            .setParameter("category_id", id).uniqueResultOptional();
                }, sf
        );
    }
}
