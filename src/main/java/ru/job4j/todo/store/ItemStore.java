package ru.job4j.todo.store;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
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
public class ItemStore {
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
            e.printStackTrace();
            throw e;
        } finally {
            session.close();
        }
    }
    public Item add(Item item) {
        return this.tx(
                session -> {
                    item.setCreated(new Timestamp(System.currentTimeMillis()));
                    session.save(item);
                    return item;
                }
        );
    }
    /**
     * Method findById. Поиск по ID.
     * @param id ID дела.
     * @return Дело
     */
    public Item findById(int id) {
        return this.tx(
                session -> session.get(Item.class, id)
        );
    }
    /**
     * Method setDone. Перевод в состояние - Выполнена.
     * @param id ID дела.
     */
    public void setDone(int id) {
        Item itm = findById(id);
        this.tx(
                session -> {
                    if (itm != null && !itm.getDone()) {
                        itm.setDone(true);
                        session.update(itm);
                    }
                    return null;
                }
                );
    }
    public void update(Item item) {
        System.out.println("TESTTTT=" + item);
        tx(session -> {
                    session.update(item);
                    return null;
                }
        );
    }
    public void delete(int id) {
        tx(session -> session.createQuery("delete from Item where id = :fId")
                .setParameter("fId", id).executeUpdate()
        );
    }
    /**
     * Method findAllItems. Получение списка текущих дел.
     * @return Дела.
     */
    public ArrayList<Item> findAllItems(final int state, User user) {
        return this.tx(
                session -> {
                    if (state == 1) {
                        if (user != null) {
                            return (ArrayList<Item>) session.createQuery("from ru.job4j.todo.model.Item where user_id = :user_id order by 1")
                                    .setParameter("user_id", user.getId()).list();
                        } else {
                            return (ArrayList<Item>) session.createQuery("from ru.job4j.todo.model.Item order by 1").list();
                        }
                    } else if (state == 2) {
                            if (user != null) {
                                return (ArrayList<Item>) session.createQuery("from ru.job4j.todo.model.Item where user_id = :user_id and done = true order by 1")
                                        .setParameter("user_id", user.getId()).list();
                            } else {
                                return (ArrayList<Item>) session.createQuery("from ru.job4j.todo.model.Item where done = false order by 1").list();
                            }
                    } else {
                        if (user != null) {
                            return (ArrayList<Item>) session.createQuery("from ru.job4j.todo.model.Item where user_id = :user_id and done = false order by 1")
                                    .setParameter("user_id", user.getId()).list();
                        } else {
                            return (ArrayList<Item>) session.createQuery("from ru.job4j.todo.model.Item where done = false order by 1").list();
                        }
                    }
                }
        );
    }
}