package chart.main;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.shape.Rectangle;

/**
 * The CustomLineChart.
 */

public class CustomLineChart<X, Y> extends LineChart<X, Y> {

  private ObservableList<Data<X, X>> verticalRangeLines;

  /**
   * The constructor.
   * 
   * @param xAxis The xAxis
   * @param yAxis The yAxis
   */
  public CustomLineChart(Axis<X> xAxis, Axis<Y> yAxis) {
    super(xAxis, yAxis);

    verticalRangeLines =
        FXCollections.observableArrayList(data -> new Observable[] {data.XValueProperty()});
    verticalRangeLines =
        FXCollections.observableArrayList(data -> new Observable[] {data.YValueProperty()});
    verticalRangeLines.addListener((InvalidationListener) observable -> layoutPlotChildren());
  }

  /**
   * Adds the vertical range lines.
   * 
   * @param node The vertical range line node
   */
  public void addVerticalRangeLines(Data<X, X> node) {
    if (!verticalRangeLines.isEmpty()) {
      if (!node.getXValue().equals(verticalRangeLines.get(0).getXValue())) {
        verticalRangeLines.get(0).setXValue(node.getXValue());
      }

      verticalRangeLines.get(0).setYValue(node.getYValue());
    } else {
      Rectangle rectangle = new Rectangle(0, 0, 0, 0);
      rectangle.getStyleClass().add("vertical-range-lines");

      node.setNode(rectangle);

      getPlotChildren().add(rectangle);
      verticalRangeLines.add(node);
    }
  }

  /**
   * Removes the vertical range lines.
   */
  public void removeVerticalRangeLines() {
    for (int i = 0; i < verticalRangeLines.size(); i++) {
      verticalRangeLines.remove(i);
      getPlotChildren().remove(i);
    }
  }

  @Override
  protected void layoutPlotChildren() {
    super.layoutPlotChildren();

    for (Data<X, X> verticalRangeLine : verticalRangeLines) {
      double xStart = getXAxis().getDisplayPosition(verticalRangeLine.getXValue());
      double xEnd = getXAxis().getDisplayPosition(verticalRangeLine.getYValue()) - xStart;

      Rectangle rectangle = (Rectangle) verticalRangeLine.getNode();
      rectangle.setX(xStart);
      rectangle.setY(0);
      rectangle.setHeight(getBoundsInLocal().getHeight());
      rectangle.setWidth(xEnd);
      rectangle.toBack();
    }
  }

}
