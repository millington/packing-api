package com.mobiquity.packer;


import com.mobiquity.exception.ApiException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Java Object used to represent an entry for a package - max weight and items.
 *
 */
public class BoxPackage {

  private static final Integer MAX_WEIGHT_PACKAGE = 100;
  private static final Integer MAX_WEIGHT_COST_ITEM = 100;
  private static final Integer MAX_ITEMS = 15;
  private static final String COMMA = ",";
  private static final String EMPTY_RESPONSE = "-";

  private List<Item> items;
  private Integer weightLimit;

  public BoxPackage(String entry) {
    parseInputLine(entry);
  }

  public Integer getWeightLimit() {
    return weightLimit;
  }

  public List<Item> getItems() {
    return items;
  }

  /**
   *  Returns String representing all packed items by index.
   *  When called, uses member variables to process all items given constraints.
   *  Package Weight must not exceed 100.
   *  Item weight/cost must not exceed 100.
   *  Max items must not exceed 15.
   *
   * @return - output string representing indices of items to be packed given constraints
   * @throws ApiException when constraints for
   */
  public String getPackedItems() throws ApiException {
    BoxPackage boxPackage = this;

    Integer packageWeightLimit = boxPackage.getWeightLimit();
    Integer numItems = boxPackage.getItems().size();
    if (packageWeightLimit > MAX_WEIGHT_PACKAGE
        || numItems > MAX_ITEMS) {
      throw new ApiException("Limits for package weight/max items exceeded: \n"
          + "Max Package Weight: " + MAX_WEIGHT_PACKAGE + "\n"
          + "Max Number of Items: " + MAX_ITEMS);
    }

    List<String> packedItems = processItems(boxPackage.getItems(), packageWeightLimit);

    if (packedItems.isEmpty()) {
      return EMPTY_RESPONSE;
    }

    Collections.sort(packedItems);
    return String.join(COMMA, packedItems);

  }

  private void parseInputLine(String input) {
    List<Item> inputItems = new ArrayList<>();

    String[] splitInput = input.split(":");
    //Process each string item in parallel - if we ever need to process more than 15 items
    Arrays.stream(splitInput[1].split(" "))
        .parallel().filter(item -> !item.isEmpty())
        .forEachOrdered(item -> inputItems.add(new Item(item)));

    this.weightLimit = Integer.valueOf(splitInput[0].strip());
    this.items = inputItems;
  }

  /**
   * Returns List of Strings representing indices of packed items within constraints.
   * Uses greedy knapsack algorithm by first sorting items by weight and adding items
   * as long as they satisfy the constraints.
   *
   * @param items - items included in entry represented by this object
   * @param packageLimit - max weight of package defined by entry
   * @return List of Strings representing indices of packed items
   * @throws ApiException when items do not satisfy constraints
   */
  private List<String> processItems(List<Item> items, Integer packageLimit) throws ApiException {
    List<String> packedItems = new ArrayList<>();
    Double currentWeight = 0.0;
    Integer currentCost = 0;

    //Sort items in the order by weight
    Collections.sort(items);
    //Iterate over all items, adding the items to the package
    for (Item item : items) {
      //Ensure constraints are satisfied
      if (item.getWeight() > packageLimit) {
        continue;
      }
      if (item.getCost() > MAX_WEIGHT_COST_ITEM || item.getWeight() > MAX_WEIGHT_COST_ITEM) {
        throw new ApiException("Limits for package weight/max items exceeded: \n"
            + "Max Package Weight: " + MAX_WEIGHT_PACKAGE + "\n"
            + "Max Number of Items: " + MAX_ITEMS);
      }

      Double newWeight = currentWeight + item.getWeight();
      Integer newCost = currentCost + item.getCost();
      //Add package and ignore further processing if weight still is below max
      if (newWeight <= packageLimit) {
        packedItems.add(item.getIndex());
        currentWeight = newWeight;
        currentCost = newCost;
      } else {
        //Unpack all items and add new item if it is worth as much or more than current cost
        if (currentCost <= item.getCost()) {
          packedItems.clear();
          packedItems.add(item.getIndex());
          currentCost = item.getCost();
          currentWeight = item.getWeight();
        }
      }
    }

    return packedItems;
  }

}
