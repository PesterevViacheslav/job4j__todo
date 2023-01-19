package ru.job4j.todo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
/**
 * Class UserController - Контроллер пользователей. Решение задач уровня Middle.
 * Категория : 3.3. HibernateТема : 3.3.2. Конфигурирование.
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 01.10.2022
 * @version 1
 */
@Controller
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/formAddUser")
    public String addUser(Model model, @RequestParam(name = "err", required = false) Boolean err) {
        model.addAttribute("err", err != null);
        return "user/addUser";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        return userService.createUser(user) == null ? "redirect:/formAddUser?err=true" : "redirect:/ok";
    }

    @GetMapping("/ok")
    public String ok() {
        return "shared/ok";
    }

    @GetMapping("/notFound")
    public String notFound() {
        return "shared/notFound";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "err", required = false) Boolean err) {
        model.addAttribute("err", err != null);
        return "shared/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> usr = userService.findUserByEmailAndPassword(
                user.getName(), user.getPassword()
        );
        if (usr.isEmpty()) {
            return "redirect:/loginPage?err=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", usr.get());
        return "redirect:/items";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}