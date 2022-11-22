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
        return "addUser";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        User usr = userService.createUser(user);
        if (usr == null) {
            return "redirect:/formAddUser?err=true";
        }
        return "redirect:/ok";
    }

    @GetMapping("/ok")
    public String ok() {
        return "ok";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "err", required = false) Boolean err) {
        model.addAttribute("err", err != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        User usr = userService.findUser(
                user.getName(), user.getPassword()
        );
        if (usr == null) {
            return "redirect:/loginPage?err=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", usr);
        return "redirect:/items";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}