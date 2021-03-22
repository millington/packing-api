package com.mobiquity.packer;

import static com.mobiquity.utils.FileUtils.checkIsValidInputFile;
import static com.mobiquity.utils.FileUtils.parseInputFile;

import com.mobiquity.exception.ApiException;
import java.util.ArrayList;
import java.util.List;

/** Class holding static packing api method.
 * The pack method accepts the absolute path to a test file
 * as a String and returns the solution as a String.
 *
 */
public class Packer {

  private static final String NEW_LINE = System.getProperty("line.separator");

  /**
   * Returns newline separated string representing successfully process packages.
   * Each line represents the items (defined by their indices) in each package.
   * A package yielding no items is represented by '-'.
   * Otherwise a representation of a processed entry is represented by '1,2,3'.
   *
   * @param filePath - absolute path to a test file in UTF-8 format
   * @return Newline separated solution string
   * @throws ApiException when incorrect filepath is provided
   */
  public static String pack(String filePath) throws ApiException {
    checkIsValidInputFile(filePath);
    List<String> packageEntries = parseInputFile(filePath);
    List<String> packages = new ArrayList<>();

    for (String entry : packageEntries) {
      packages.add(new BoxPackage(entry).getPackedItems());
    }

    return String.join(NEW_LINE, packages);
  }

}
