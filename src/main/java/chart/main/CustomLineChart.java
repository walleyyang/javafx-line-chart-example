package chart.main;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * The CustomLineChart.
 */

public class CustomLineChart<X, Y> extends LineChart<X, Y> {

  private ObservableList<Data<X, Y>> verticalLines;
  private ObservableList<Data<X, X>> verticalRangeLines;

  /**
   * The constructor.
   * 
   * @param xAxis The xAxis
   * @param yAxis The yAxis
   */
  public CustomLineChart(Axis<X> xAxis, Axis<Y> yAxis) {
    super(xAxis, yAxis);

    verticalLines =
        FXCollections.observableArrayList(data -> new Observable[] {data.XValueProperty()});
    verticalLines.addListener((InvalidationListener) observable -> layoutPlotChildren());

    verticalRangeLines =
        FXCollections.observableArrayList(data -> new Observable[] {data.XValueProperty()});
    verticalRangeLines.addListener((InvalidationListener) observable -> layoutPlotChildren());
  }

  /**
   * Add the vertical line.
   * 
   * @param node The vertical line node
   */
  public void addVerticalLine(Data<X, Y> node) {
    if (verticalLines.contains(node)) {
      return;
    }

    Line line = new Line();
    node.setNode(line);
    getPlotChildren().add(line);
    verticalLines.add(node);
  }

  /**
   * Removes the vertical line.
   * 
   * @param node The vertical line node
   */
  public void removeVerticalLine(Data<X, Y> node) {
    if (node.getNode() != null) {
      getPlotChildren().remove(node.getNode());
      node.setNode(null);
    }

    verticalLines.remove(node);
  }

  /**
   * Adds the vertical range lines.
   * 
   * @param node The vertical range line node
   */
  public void addVerticalRangeLines(Data<X, X> node) {
    if (verticalRangeLines.contains(node)) {
      return;
    }

    Rectangle rectangle = new Rectangle(0, 0, 0, 0);
    rectangle.getStyleClass().add("vertical-range-lines");
    node.setNode(rectangle);
    getPlotChildren().add(rectangle);
    
    verticalRangeLines.add(node);
  }

  /**
   * Removes the vertical range lines.
   * 
   * @param node The vertical range line node
   */
  public void removeVerticalRangeLines(Data<X, X> node) {
    if (node.getNode() != null) {
      getPlotChildren().remove(node.getNode());
      node.setNode(null);
    }

    verticalRangeLines.remove(node);
  }

  @Override
  protected void layoutPlotChildren() {
    super.layoutPlotChildren();

    double number = 5;

    for (Data<X, Y> verticalMarker : verticalLines) {
      Line line = (Line) verticalMarker.getNode();
      line.setStartX(getXAxis().getDisplayPosition(verticalMarker.getXValue()) + number);
      line.setEndX(line.getStartX());
      line.setStartY(0d);
      line.setEndY(getBoundsInLocal().getHeight());
      line.toFront();
    }

    for (Data<X, X> verticalRangeMarker : verticalRangeLines) {

      Rectangle rectangle = (Rectangle) verticalRangeMarker.getNode();
      rectangle.setX(getXAxis().getDisplayPosition(verticalRangeMarker.getXValue()) + number);
      rectangle.setWidth(getXAxis().getDisplayPosition(verticalRangeMarker.getYValue())
          - getXAxis().getDisplayPosition(verticalRangeMarker.getXValue()));
      rectangle.setY(0d);
      rectangle.setHeight(getBoundsInLocal().getHeight());
      rectangle.toBack();
    }
  }

}
