package com.mobiquity.packer;

import java.util.Objects;

/** Java Class representing item object to describe single item in BoxPackage.
 *
 */
public class Item implements Comparable<Item> {

  private final String index;
  private final Double weight;
  private final Integer cost;

  /** Constructor to create item object with dedicated index, weight, and cost.
   *
   * @param index - string indicating index of item
   * @param weight - weight as double indicating weight of item
   * @param cost - cost as integer indicating cost of item
   */
  public Item(String index, Double weight, Integer cost) {
    this.index = index;
    this.weight = weight;
    this.cost = cost;
  }

  /** Constructor to create item object with dedicated
   *  index, weight, and cost from comma separated string.
   * Parses string and sets index, weight, and cost assuming format '(1,2.0,€3)'
   *
   * @param inputString - string representing item object in format "(index,weight,cost)"
   */
  public Item(String inputString) {
    String[] splitItem = inputString.replaceAll("[()]|€", "").split(",");
    this.index = splitItem[0].strip();
    this.weight = Double.valueOf(splitItem[1].strip());
    this.cost = Integer.valueOf(splitItem[2].strip());
  }


  public String getIndex() {
    return index;
  }

  public Double getWeight() {
    return weight;
  }

  public Integer getCost() {
    return cost;
  }

  @Override
  public int compareTo(Item item) {
    Double compareWeight = item.getWeight();
    return (int) (compareWeight - this.weight);
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
    return Objects.equals(index, item.index)
        && Objects.equals(weight, item.weight)
        && Objects.equals(cost, item.cost);
  }

}
