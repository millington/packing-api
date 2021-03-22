package com.mobiquity.utils;


import static java.nio.charset.StandardCharsets.UTF_8;

import com.mobiquity.exception.ApiException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Utilities class in packer-api to validate/process files for separation of concerns.
 *
 */
public class FileUtils {

  /**
   * Returns boolean representing whether the provided path is valid.
   * The path argument must be a valid absolute path to a file on the filesystem.
   *
   * @param path - string identifying absolute path to file to be validated
   * @return boolean - identifying that provided filepath is valid
   * @throws ApiException - when path is empty, directory, non-existent, or relative
   */
  public static boolean checkIsValidInputFile(String path) throws ApiException {
    File file = new File(path);
    if (path.isEmpty()) {
      throw new ApiException("Empty Filepath provided");
    }
    if (!file.isAbsolute()) {
      throw new ApiException("Provided relative path, absolute path required:" + path);
    }
    if (!file.exists()) {
      throw new ApiException("Filepath does not exist:" + path);
    }
    if (!file.isFile()) {
      throw new ApiException("Filepath is not file:" + path);
    }
    return true;
  }


  /**
   * Returns a list of strings representing each line of file.
   * The path argument must be a valid absolute path to a file on the filesystem.
   *
   * @param filePath - string identifying absolute path to file to be validated
   * @return List of line strings from file splitting based on newline
   * @throws ApiException - when fails to process all lines in file
   */
  public static List<String> parseInputFile(String filePath) throws ApiException {
    List<String> output = new ArrayList<>();
    try (FileReader fr = new FileReader(filePath, UTF_8);
         BufferedReader reader = new BufferedReader(fr)) {
      String line;
      while ((line = reader.readLine()) != null) {
        output.add(line);
      }
    } catch (IOException e) {
      throw new ApiException("Caught IOException while reading file: " + filePath, e);
    }
    if (output.isEmpty()) {
      throw new ApiException("No lines in filepath:" + filePath);
    }
    return output;
  }
}
