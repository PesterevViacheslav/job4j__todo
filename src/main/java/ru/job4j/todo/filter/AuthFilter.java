package ru.job4j.todo.filter;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class AuthFilter - Фильтр аутентификации. Решение задач уровня Middle.
 * Категория : 3.3. HibernateТема : 3.3.2. Конфигурирование.
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 01.10.2022
 * @version 1
 */
@Component
public class AuthFilter implements Filter {
    private final Set<String> pages = new HashSet<>(Arrays.asList("loginPage",
                                                                  "login",
                                                                  "formAddUser",
                                                                  "registration",
                                                                  "ok",
                                                                  "items",
                                                                  "notFound"));

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest reg = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = reg.getRequestURI();

        if (pages.stream().anyMatch(page -> uri.endsWith(page))) {
            chain.doFilter(reg, res);
            return;
        }
        if (reg.getSession().getAttribute("user") == null) {
            res.sendRedirect(reg.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(reg, res);
    }
}
