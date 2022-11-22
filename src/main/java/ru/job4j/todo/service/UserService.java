package ru.job4j.todo.service;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserStore;
/**
 * Class UserService - Сервис обработки действий с пользователями. Решение задач уровня Middle.
 * Категория : 3.3. HibernateТема : 3.3.2. Конфигурирование.
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 01.10.2022
 * @version 1
 */
@Service
public class UserService {
    private final UserStore userStore;
    public UserService(UserStore userStore) {
        this.userStore = userStore;
    }
    public User createUser(User user) {
        return userStore.createUser(user);
    }
    public User findUser(String name, String pwd) {
        return userStore.findUser(name, pwd);
    }
}