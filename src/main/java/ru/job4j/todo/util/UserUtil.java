package ru.job4j.todo.util;
import org.springframework.ui.Model;
import ru.job4j.todo.model.User;
import javax.servlet.http.HttpSession;

public class UserUtil {
    public static User getUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("unknown");
        }
        model.addAttribute("user", user);
        return user;
    }
}
