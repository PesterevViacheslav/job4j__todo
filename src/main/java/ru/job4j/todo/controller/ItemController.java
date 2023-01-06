package ru.job4j.todo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.ItemService;
import ru.job4j.todo.util.UserUtil;

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
        User user = UserUtil.getUser(model, session);
        model.addAttribute("items", itemService.findAllItems(1, user));
        return "item/items";
    }

    @GetMapping("/formNew")
    public String allNew(Model model, HttpSession session) {
        User user = UserUtil.getUser(model, session);
        model.addAttribute("items", itemService.findAllItems(3, user));
        return "item/items";
    }

    @GetMapping("/formDone")
    public String allDone(Model model, HttpSession session) {
        User user = UserUtil.getUser(model, session);
        model.addAttribute("items", itemService.findAllItems(2, user));
        return "item/items";
    }

    @GetMapping("/formDesc/{itemId}")
    public String descItem(Model model, HttpSession session, @PathVariable("itemId") int id) {
        User user = UserUtil.getUser(model, session);
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        if (user == null || item == null) {
            return "redirect:/notFound";
        } else {
            return "item/descItem";
        }
    }

    @GetMapping("/formUpdate/{itemId}")
    public String formUpdate(HttpServletRequest req, Model model, HttpSession session, @PathVariable("itemId") int id) {
        User user = UserUtil.getUser(model, session);
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        req.setAttribute("item_id", item.getId());
        if (user == null || item == null) {
            return "redirect:/notFound";
        } else {
            return "item/updateItem";
        }
    }
    @PostMapping("/updateItem")
    public String updateItem(HttpServletRequest req, @ModelAttribute Item item) {
        if (item == null) {
            return "redirect:/notFound";
        } else {
            itemService.update(item);
            return "redirect:/items";
        }
    }

    @GetMapping("/formAdd")
    public String formAdd(@ModelAttribute Item item, Model model, HttpSession session) {
        UserUtil.getUser(model, session);
        return "item/addItem";
    }
    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item) {
        itemService.add(item);
        return "redirect:/items";
    }

    @GetMapping("/formDelete/{itemId}")
    public String deleteItem(@PathVariable("itemId") int id) {
        if (id == 0) {
            return "redirect:/shared/notFound";
        } else {
            itemService.delete(id);
            return "redirect:/items";
        }
    }

    @GetMapping("/formDone/{itemId}")
    public String doneItem(@PathVariable("itemId") int id) {
        if (id == 0) {
            return "redirect:/shared/notFound";
        } else {
            itemService.setDone(id);
            return "redirect:/items";
        }
    }
}