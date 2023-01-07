package ru.job4j.todo.store;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;
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
        return Optional.of(this.tx(
                session -> session.get(Item.class, id), sf
        ));
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
     * Method findAllItems. Получение списка текущих дел.
     * @return Дела.
     */
    public ArrayList<Item> findAllItems(final int state, User user) {
        return this.tx(
                session -> {
                    return (ArrayList<Item>) session.createQuery(" from ru.job4j.todo.model.Item"
                                    + " where user_id = :user_id"
                                    + "   and (:state = 1 or :state = 2 and done = true or :state = 3 and done = false)"
                                    + " order by 1")
                            .setParameter("user_id", user.getId()).setParameter("state", state).list();
                }, sf
        );
    }
}