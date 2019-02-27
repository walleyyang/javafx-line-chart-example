package chart.main;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * The ChartViewModel.
 */
public class ChartViewModel {

  private ListProperty<String> items;
  private ListProperty<Chart> charts;

  /**
   * The constructor.
   */
  public ChartViewModel() {
    items = new SimpleListProperty<>(FXCollections.observableArrayList());
    charts = new SimpleListProperty<>(FXCollections.observableArrayList());
  }

  /**
   * Adds new items list.
   */
  public void addNewItem() {
    items.add("Chart " + (items.getSize() + 1));
  }

  /**
   * Adds new chart.
   * 
   * @param title The title
   */
  public void addNewChart(String title) {
    charts.add(new Chart(title));
  }

  /**
   * Gets the items.
   * 
   * @return The items
   */
  public ListProperty<String> getItemsProperty() {
    return items;
  }

  /**
   * Gets the charts.
   * 
   * @return The charts
   */
  public ListProperty<Chart> getChartsProperty() {
    return charts;
  }

}
