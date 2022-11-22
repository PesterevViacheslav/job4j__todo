package ru.job4j.todo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.ItemService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * Class ItemController - Контроллер заданий. Решение задач уровня Middle.
 * Категория : 3.3. HibernateТема : 3.3.2. Конфигурирование.
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 01.10.2022
 * @version 1
 */
@Controller
public class ItemController {
    private final ItemService itemService;
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String items(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("unknown");
        }
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findAllItems(1, user));
        return "items";
    }

    @GetMapping("/formNew")
    public String allNew(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findAllItems(3, user));
        return "items";
    }

    @GetMapping("/formDone")
    public String allDone(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findAllItems(2, user));
        return "items";
    }

    @GetMapping("/formDesc/{itemId}")
    public String descItem(
            Model model, HttpSession session, @PathVariable("itemId") int id) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "descItem";
    }

    @GetMapping("/formUpdate/{itemId}")
    public String formUpdate(HttpServletRequest req, Model model, HttpSession session, @PathVariable("itemId") int id) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        req.setAttribute("item_id", item.getId());
        return "updateItem";
    }
    @PostMapping("/updateItem")
    public String updateItem(HttpServletRequest req, @ModelAttribute Item item) {
        itemService.update(item);
        return "redirect:/items";
    }

    @GetMapping("/formAdd")
    public String formAdd(@ModelAttribute Item item, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "addItem";
    }
    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item) {
        itemService.add(item);
        return "redirect:/items";
    }

    @GetMapping("/formDelete/{itemId}")
    public String deleteItem(@PathVariable("itemId") int id) {
        itemService.delete(id);
        return "redirect:/items";
    }

    @GetMapping("/formDone/{itemId}")
    public String doneItem(@PathVariable("itemId") int id) {
        itemService.setDone(id);
        return "redirect:/items";
    }
}
