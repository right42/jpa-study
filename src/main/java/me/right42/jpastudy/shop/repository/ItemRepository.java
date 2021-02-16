package me.right42.jpastudy.shop.repository;

import lombok.RequiredArgsConstructor;
import me.right42.jpastudy.shop.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager entityManager;

    public void save(Item item) {
        if (item.getId() == null) {
            entityManager.persist(item);
        } else {
            entityManager.merge(item);
        }
    }

    public Item findOne(Long itemId) {
        return entityManager.find(Item.class, itemId);
    }

    public List<Item> findAll() {
        return entityManager.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
