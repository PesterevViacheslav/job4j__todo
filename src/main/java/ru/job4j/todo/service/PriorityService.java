package ru.job4j.todo.service;
import org.springframework.stereotype.Service;
import ru.job4j.todo.store.PriorityStore;
import java.util.List;
/**
 * Class PriorityService - Сервис обработки приоритетов. Решение задач уровня Middle.
 * Категория : 3.3. HibernateТема : 3.3.2. Конфигурирование.
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 23.02.2023
 * @version 1
 */
@Service
public class PriorityService {
    private final PriorityStore priorityStore;
    public PriorityService(PriorityStore store) {
        this.priorityStore = store;
    }
    public List findAllPriorities() {
        return priorityStore.findAllPriorities();
    }
}
