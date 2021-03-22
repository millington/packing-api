package com.mobiquity.packer;

import com.mobiquity.exception.ApiException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoxPackageTest {

    @Test
    void assertParseInventoryString() {
        Integer expectedWeight = 1;
        Item item = new Item("0",0.0,0);

        BoxPackage output = new BoxPackage("1 : (0,0.0,€0)");
        Integer outputWeight = output.getWeightLimit();
        List<Item> outputItems = output.getItems();

        assertEquals(expectedWeight, outputWeight);
        assertEquals(List.of(item), outputItems);
    }

    @Test
    void assertProcessSingleItemsCorrectly() throws ApiException {
        String expectedOutput = "0";

        BoxPackage output = new BoxPackage("2 : (0,2.0,€0)");
        output.getPackedItems();
        assertEquals(expectedOutput, output.getPackedItems());
    }

    @Test
    void assertProcessSingleItemsAboveMaxWeightCorrectly() throws ApiException {
        String expectedOutput = "-";

        BoxPackage output = new BoxPackage("1 : (0,2.0,€0)");
        output.getPackedItems();
        assertEquals(expectedOutput, output.getPackedItems());
    }

    @Test
    void assertProcessMultipleItemsCorrectly() throws ApiException {
        String expectedOutput = "1";

        BoxPackage output = new BoxPackage("2 : (0,2.0,€0) (1,1.9,€0)");
        output.getPackedItems();
        assertEquals(expectedOutput, output.getPackedItems());
    }

    @Test
    void assertProcessMultipleItemsWithinWeightCorrectly() throws ApiException {
        String expectedOutput = "0,1";

        BoxPackage output = new BoxPackage("4 : (0,2.0,€0) (1,2.0,€0)");
        output.getPackedItems();
        assertEquals(expectedOutput, output.getPackedItems());
    }
}