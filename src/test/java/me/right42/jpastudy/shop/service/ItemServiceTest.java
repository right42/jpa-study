package me.right42.jpastudy.shop.service;

import me.right42.jpastudy.shop.domain.item.Book;
import me.right42.jpastudy.shop.domain.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Test
    @Transactional
    void saveItemTest(){
        Item item = new Book();
        item.setName("test book");

        itemService.saveItem(item);

        Item findItem = itemService.findOne(item.getId());

        assertThat(findItem).isEqualTo(item);
    }

}