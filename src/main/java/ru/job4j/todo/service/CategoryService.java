package ru.job4j.todo.service;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryStore;
import java.util.List;
import java.util.Optional;
/**
 * Class CategoryService - Сервис обработки категорий. Решение задач уровня Middle.
 * Категория : 3.3. HibernateТема : 3.3.2. Конфигурирование.
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 23.02.2023
 * @version 1
 */
@Service
public class CategoryService {
    private final CategoryStore categoryStore;
    public CategoryService(CategoryStore store) {
        this.categoryStore = store;
    }
    public List findAllCategories() {
        return categoryStore.findAllCategories();
    }
    public List<Category> findByIdList(List<Integer> idList) {
        return categoryStore.findByIdList(idList);
    }
}