package chart.main;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
      rectangle.setStroke(Color.TRANSPARENT);
      rectangle.setFill(Color.ALICEBLUE.deriveColor(1, 1, 1, 0.2));

      node.setNode(rectangle);

      getPlotChildren().add(rectangle);
      verticalRangeLines.add(node);
    }
  }

  /**
   * Removes the vertical range lines.
   * 
   * @param node The vertical range line node
   */
  public void removeVerticalRangeLines(Data<X, X> node) {
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

      System.out.println("xStart: " + xStart);
      System.out.println("xEnd: " + xEnd);

      Rectangle rectangle = (Rectangle) verticalRangeLine.getNode();
      rectangle.setX(0);
      rectangle.setY(0d);
      rectangle.setHeight(getBoundsInLocal().getHeight());
      rectangle.setWidth(30);
      rectangle.toBack();
    }
  }

}
