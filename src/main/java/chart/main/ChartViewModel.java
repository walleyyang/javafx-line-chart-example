package chart.main;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * The ChartViewModel.
 */
public class ChartViewModel {

  private ListProperty<String> items;
  
  /**
   * The constructor.
   */
  public ChartViewModel() {
    items = new SimpleListProperty<>(FXCollections.observableArrayList());
  }

  /**
   * Adds new items list.
   */
  public void addNewItem() {
    items.add("Chart " + (items.getSize() + 1));
  }
  
  /**
   * Gets the items.
   * 
   * @return The items
   */
  public ListProperty<String> getItemsProperty() {
    return items;
  }

}
