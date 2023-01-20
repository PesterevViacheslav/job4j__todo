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
        Optional<Item> res = itemStore.findById(id);
        res.ifPresent(itm -> itemStore.setDone(id));
        return res.isPresent();
    }
    public boolean update(Item item) {
        Optional<Item> res = itemStore.findById(item.getId());
        res.ifPresent(itm -> itemStore.update(item));
        return res.isPresent();
    }
    public boolean delete(int id) {
        Optional<Item> res = itemStore.findById(id);
        res.ifPresent(itm -> itemStore.delete(id));
        return res.isPresent();
    }
}
