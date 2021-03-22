package com.mobiquity.packer;

import com.mobiquity.exception.ApiException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.mobiquity.packer.Packer.pack;
import static org.junit.jupiter.api.Assertions.*;

class PackerTest {

    @Test
    void assertFailsOnEmptyFilepath() {
        Exception exception = assertThrows(ApiException.class, () -> {
            pack("");
        });
        String expectedMessage = "Empty Filepath provided";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void assertFailsOnEmptyFile() {
        Exception exception = assertThrows(ApiException.class, () -> {
            pack(toAbsolute("src/test/resources/input_data/empty_input"));
        });
        String expectedMessage = "No lines in filepath";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void assertFailsWithRelativePath() {
        Exception exception = assertThrows(ApiException.class, () -> {
            pack("example_input");
        });
        String expectedMessage = "Provided relative path, absolute path required:example_input";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void assertProcessExample() throws IOException, ApiException {
        checkCorrectlyPackedFromInput("src/test/resources/input_data/example_input",
            "src/test/resources/expected_data/example_output");
    }

    @Test
    void assertProcessSingleEntry() throws IOException, ApiException {
        checkCorrectlyPackedFromInput("src/test/resources/input_data/single_entry_input",
            "src/test/resources/expected_data/single_entry_output");
    }

    @Test
    void assertTakeLighterItemEqualCost() throws IOException, ApiException {
        checkCorrectlyPackedFromInput("src/test/resources/input_data/equal_cost_input",
            "src/test/resources/expected_data/equal_cost_output");
    }

    @Test
    void assertProcessEntriesLessThanEuro() throws IOException, ApiException {
        checkCorrectlyPackedFromInput("src/test/resources/input_data/pennies_cost_input",
            "src/test/resources/expected_data/pennies_cost_output");
    }

    @Test
    void assertThrowsExceptionWhenExceedsMaxWeight() {
        checkThrowsExceptionWhenExceedsLimits("src/test/resources/input_data/exceed_max_weight_input");
    }

    @Test
    void assertThrowsExceptionWhenExceedsMaxItems() {
        checkThrowsExceptionWhenExceedsLimits("src/test/resources/input_data/max_items_input");
    }

    @Test
    void assertIgnoresItemWhenExceedsMaxWeight() throws IOException, ApiException {
        checkCorrectlyPackedFromInput("src/test/resources/input_data/max_weight_input",
            "src/test/resources/expected_data/max_weight_output");

    }

    @Test
    void assertProcessManyEntries() throws IOException, ApiException {
        checkCorrectlyPackedFromInput("src/test/resources/input_data/many_entries_input",
            "src/test/resources/expected_data/many_entries_output");

    }

    private void checkCorrectlyPackedFromInput(String inputPath, String expectedPath) throws IOException, ApiException {
        String expected = Files.readString(Path.of(toAbsolute(expectedPath)));
        String output = pack(toAbsolute(inputPath));
        assertEquals(expected, output);
    }

    private void checkThrowsExceptionWhenExceedsLimits(String inputPath) {
        Exception exception = assertThrows(ApiException.class,
            () -> pack(toAbsolute(inputPath)));

        String expectedMessage = "Limits for package weight/max items exceeded: \n" +
            "Max Package Weight: 100\n" +
            "Max Number of Items: 15";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    private String toAbsolute(String path) {
        return Path.of(path).toAbsolutePath().toString();
    }
}
