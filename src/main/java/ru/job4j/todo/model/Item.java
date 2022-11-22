package ru.job4j.todo.model;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
@Entity
@Table(name = "item")
/**
 * Class Item - Событие.
 * Решение задач уровня Middle. Части 3.3. Hibernate.
 * Создать TODO list [#3786]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 01.10.2022
 * @version 1
 */
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "description")
    private String description;
    @Column(name = "created")
    private Timestamp created;
    @Column(name = "done")
    private boolean done;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Method getId. Получение id дела
     * @return id дела
     */
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method getDescription. Получение описания дела
     * @return Описание
     */

    public String getDescription() {
        return this.description;
    }
    /**
     * Method setDescription. Установка комментария
     * @param description Комментарий
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Method setCreated. Установка даты создания
     * @param created Дата создания
     */
    public void setCreated(Timestamp created) {
        this.created = created;
    }
    /**
     * Method getCreated. Получение даты создания дела
     * @return дата создания
     */
    public Timestamp getCreated() {
        return created;
    }
    /**
     * Method getDone. Получение статуса дела
     * @return статус
     */
    public boolean getDone() {
        return done;
    }
    /**
     * Method setDone. Изменение статуса дела
     * @param done статус
     */
    public void setDone(boolean done) {
        this.done = done;
    }
    /**
     * Method getUser. Получение ответственного для дела
     * @return пользователь
     */
    public User getUser() {
        return user;
    }
    /**
     * Method setUser. Изменение ответственного для дела
     * @param user Пользователь
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", description='" + description + '\''
                + ", created=" + created + ", done=" + done + '}';
    }
}