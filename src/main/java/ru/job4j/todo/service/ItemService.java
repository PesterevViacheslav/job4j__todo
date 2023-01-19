package ru.job4j.todo.service;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.ItemStore;

import java.util.List;
import java.util.Optional;

/**
 * Class ItemService - Сервис обработки действий с заданиями. Решение задач уровня Middle.
 * Категория : 3.3. HibernateТема : 3.3.2. Конфигурирование.
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 01.10.2022
 * @version 1
 */
@Service
public class ItemService {
    private final ItemStore itemStore;
    public ItemService(ItemStore store) {
        this.itemStore = store;
    }
    public void add(Item item) {
        itemStore.add(item);
    }
    public Optional<Item> findById(int id) {
        return itemStore.findById(id);
    }
    public List findAllItems(int state, User user) {
        return itemStore.findAllItems(state, user);
    }
    public boolean setDone(int id) {
        boolean res = true;
        if (itemStore.findById(id).isEmpty()) {
            res = false;
        } else {
            itemStore.setDone(id);
        }
        return res;
    }
    public boolean update(Item item) {
        boolean res = true;
        if (itemStore.findById(item.getId()).isEmpty()) {
            res = false;
        } else {
            itemStore.update(item);
        }
        return res;
    }
    public boolean delete(int id) {
        boolean res = false;
        itemStore.delete(id);
        if (itemStore.findById(id).isEmpty()) {
            res = true;
        }
        return res;
    }
}
