package ru.job4j.todo.util;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import java.time.ZoneId;

public final class ItemUtil {
    private ItemUtil() {
    }
    public static Item setTz(Item item, User user) {
        item.setCreated(item.getCreated()
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of(user.getTz()))
                .toLocalDateTime());
        return item;
    }
}
