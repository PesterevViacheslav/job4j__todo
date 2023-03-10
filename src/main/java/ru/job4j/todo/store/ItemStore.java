package ru.job4j.todo.store;
import org.hibernate.SessionFactory;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
/**
 * Class ItemStore - Хранилище заданий в БД postgres.
 * Решение задач уровня Middle. Части 3.3. Hibernate.
 * Создать TODO list [#3786]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 01.10.2022
 * @version 1
 */
@Repository
@AllArgsConstructor
public class ItemStore implements Store {
    private final SessionFactory sf;
    public Item add(Item item) {
        return this.tx(
                session -> {
                    item.setCreated(new Timestamp(System.currentTimeMillis()));
                    session.save(item);
                    return item;
                }, sf
        );
    }
     /**
     * Method findById. Поиск по ID.
     * @param id ID дела.
     * @return Дело
     */
    public Optional<Item> findById(int id) {
        return this.tx(
                session -> {
                    return session.createQuery(" from Item i JOIN FETCH i.priority LEFT JOIN FETCH i.categories"
                                   + " where i.id = :item_id")
                           .setParameter("item_id", id).uniqueResultOptional();
                }, sf
        );
    }
     /**
     * Method setDone. Перевод в состояние - Выполнена.
     * @param id ID дела.
     */
    public void setDone(int id) {
        tx(session -> session.createQuery("update Item set done = true where id = :fId")
                .setParameter("fId", id).executeUpdate(), sf
        );
    }
    public void update(Item item) {
        tx(session -> {
                    session.update(item);
                    return null;
                }, sf
        );
    }
    public void delete(int id) {
        tx(session -> session.createQuery("delete from Item where id = :fId")
                .setParameter("fId", id).executeUpdate(), sf
        );
    }
     /**
     * Method findAllItems. Получение списка всех дел.
     * @return Дела.
     */
    public List<Item> findAllItems(User user) {
        return this.tx(
                session -> {
                    return session.createQuery("SELECT DISTINCT i FROM Item i JOIN FETCH i.priority LEFT JOIN FETCH i.categories"
                                    + " where user_id = :user_id"
                                    + " order by priority_id")
                            .setParameter("user_id", user.getId()).list();
                }, sf
        );
    }
    /**
     * Method findAllItems. Получение списка выполненных дел.
     * @return Дела.
     */
    public List<Item> findDoneItems(User user) {
        return this.tx(
                session -> {
                    return session.createQuery("SELECT DISTINCT i FROM Item i JOIN FETCH i.priority LEFT JOIN FETCH i.categories"
                                    + " where user_id = :user_id"
                                    + "   and done = true"
                                    + " order by priority_id")
                            .setParameter("user_id", user.getId()).list();
                }, sf
        );
    }
    /**
     * Method findAllItems. Получение списка текущих дел.
     * @return Дела.
     */
    public List<Item> findNewItems(User user) {
        return this.tx(
                session -> {
                    return session.createQuery("SELECT DISTINCT i FROM Item i JOIN FETCH i.priority LEFT JOIN FETCH i.categories"
                                    + " where user_id = :user_id"
                                    + "   and done = false"
                                    + " order by priority_id")
                            .setParameter("user_id", user.getId()).list();
                }, sf
        );
    }
}