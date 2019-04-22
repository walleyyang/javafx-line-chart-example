package chart.main;

import java.util.Objects;
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

  private ObservableList<Data<X, X>> verticalRangeLines;
  private ObservableList<Data<Y, Y>> horizontalRangeLines;

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
    horizontalRangeLines = FXCollections.observableArrayList(data -> new Observable[] {data.YValueProperty()});
    horizontalRangeLines.addListener((InvalidationListener) observable -> layoutPlotChildren());
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
  
  public void addHorizontalRangeLines(Data<Y, Y> node) {
    if (horizontalRangeLines.contains(node)) {
      return;
    } else {
      Line line = new Line();
      node.setNode(line);;
      getPlotChildren().add(line);
      horizontalRangeLines.add(node);
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
    
    for (Data<Y, Y> horizontalRangeLine : horizontalRangeLines) {
      Line line = (Line) horizontalRangeLine.getNode();
      line.setStartX(0);
      line.setEndX(getBoundsInLocal().getWidth());
      line.setStartY(getYAxis().getDisplayPosition(horizontalRangeLine.getYValue()));
      line.setEndY(line.getStartY());
      line.toBack();
    }
  }

}
