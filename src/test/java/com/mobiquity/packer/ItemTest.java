package com.mobiquity.packer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemTest {
    @Test
    void assertParseItemString() {
        String expectedIndex = "dummyIndex";
        Double expectedWeight = 0.0;
        Integer expectedCost = 0;
        Item item = new Item("(dummyIndex,0.0,€0)");
        assertEquals(expectedIndex, item.getIndex());
        assertEquals(expectedWeight, item.getWeight());
        assertEquals(expectedCost, item.getCost());
    }

    @Test
    void assertParseItemStringWithoutCurrency() {
        String expectedIndex = "dummyIndex";
        Double expectedWeight = 0.0;
        Integer expectedCost = 0;
        Item item = new Item("(dummyIndex,0.0,0)");
        assertEquals(expectedIndex, item.getIndex());
        assertEquals(expectedWeight, item.getWeight());
        assertEquals(expectedCost, item.getCost());
    }

    @Test
    void assertParseItemStringWithSpaces() {
        String expectedIndex = "dummyIndex";
        Double expectedWeight = 0.0;
        Integer expectedCost = 0;
        Item item = new Item("(dummyIndex , 0.0 , €0)");
        assertEquals(expectedIndex, item.getIndex());
        assertEquals(expectedWeight, item.getWeight());
        assertEquals(expectedCost, item.getCost());
    }

    @Test
    void assertItemListSortedDescending() {
        List<Item> items = new ArrayList<>();
        String expectedIndex = "2";
        Double expectedWeight = 20.0;
        Integer expectedCost = 5;

        items.add(new Item("(1,10.0,€10)"));
        items.add(new Item("(2,20.0,€5)"));
        Collections.sort(items);
        Item firstItem = items.get(0);
        assertEquals(expectedIndex, firstItem.getIndex());
        assertEquals(expectedWeight, firstItem.getWeight());
        assertEquals(expectedCost, firstItem.getCost());
    }
    @Test
    void assertThrowExceptionParseEmptyItemString() {
        assertThrows(IndexOutOfBoundsException.class, () -> new Item(""));
    }


    @Test
    void assertThrowExceptionParseBadItemString() {
        assertThrows(IndexOutOfBoundsException.class, () -> new Item("(dummyIndex,0)"));
    }

    @Test
    void assertThrowExceptionParseConcatenatedItemString() {
        assertThrows(IndexOutOfBoundsException.class, () -> new Item("(dummyIndex0.00)"));
    }

}
