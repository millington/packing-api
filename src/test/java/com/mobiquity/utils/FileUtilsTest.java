package com.mobiquity.utils;

import com.mobiquity.exception.ApiException;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static com.mobiquity.utils.FileUtils.checkIsValidInputFile;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileUtilsTest {

    @Test
    void assertThrowsExceptionOnEmptyFile() {
        Exception exception = assertThrows(ApiException.class, () ->
            checkIsValidInputFile("")
        );
        String expectedMessage = "Empty Filepath provided";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void assertThrowsExceptionRelative() {
        Exception exception = assertThrows(ApiException.class, () ->
            checkIsValidInputFile("input_data/example_input")
        );
        String expectedMessage = "Provided relative path, absolute path required:input_data/example_input";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void assertThrowsExceptionIfFileNotExists() {
        Exception exception = assertThrows(ApiException.class, () ->
            checkIsValidInputFile("/tmp/fakefile")
        );
        String expectedMessage = "Filepath does not exist:/tmp/fakefile";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void assertThrowsExceptionOnDirAsPath() {
        Exception exception = assertThrows(ApiException.class, () ->
            checkIsValidInputFile("/tmp")
        );
        String expectedMessage = "Filepath is not file:/tmp";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void assertReturnsTrueOnValidFile() throws ApiException {
        assertTrue(checkIsValidInputFile(Path.of("src/test/resources/input_data/example_input").toAbsolutePath().toString()));
    }

}