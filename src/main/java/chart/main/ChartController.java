package chart.main;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * The ChartView.
 */
public class ChartController {

  private ChartViewModel chartViewModel;
  private CustomLineChart<Number, Number> lineChart;
  private NumberAxis xAxis;
  private NumberAxis yAxis;
  private XYChart.Series<Number, Number> series;
  private boolean mouseDragged;
  private double initialNumberStart;
  private double initialNumberEnd;

  @FXML
  private VBox mainContainer;

  @FXML
  private HBox chartContainer;


  /**
   * The constructor.
   */
  public ChartController() {
    chartViewModel = new ChartViewModel();
    mouseDragged = false;
  }

  /**
   * The initialize method.
   */
  public void initialize() {
    createChart();
    handleEvents();
  }

  /**
   * Handles the events.
   */
  private void handleEvents() {
    lineChart.setOnMousePressed(pressed -> {
      int minSize = 1;
      // Get coordinate from the scene and transform to coordinates from the chart axis
      Point2D firstSceneCoordinate = new Point2D(pressed.getSceneX(), pressed.getSceneY());
      double firstX = xAxis.sceneToLocal(firstSceneCoordinate).getX();

      lineChart.setOnMouseDragged(dragged -> {
        mouseDragged = true;

        Point2D draggedSceneCoordinate = new Point2D(dragged.getSceneX(), dragged.getSceneY());
        double draggedX = xAxis.sceneToLocal(draggedSceneCoordinate).getX();

        List<Double> numbers = filterSeries(firstX, draggedX);
        double numberStart = numbers.size() > minSize ? numbers.get(0) : initialNumberStart;
        double numberEnd =
            numbers.size() > minSize ? numbers.get(numbers.size() - 1) : initialNumberEnd;

        if (numbers.size() > minSize) {
          lineChart.addVerticalRangeLines(new Data<>(numberStart, numberEnd));
        }

        lineChart.setOnMouseReleased(released -> {
          if (mouseDragged) {
            initialNumberStart = numberStart;
            initialNumberEnd = numberEnd;
            // redrawChart();
            mouseDragged = false;
            
          }
        });
      });
    });
  }

  /**
   * Creates the charts.
   */
  private void createChart() {
    xAxis = new NumberAxis();
    yAxis = new NumberAxis();
    series = new XYChart.Series<>();
    lineChart = new CustomLineChart<>(xAxis, yAxis);
    
    List<Integer> data = chartViewModel.getData();
    
    for (int i = 0; i < data.size(); i++) {
      series.getData().add(new XYChart.Data<Number, Number>(i, data.get(i)));
    }

    lineChart.getData().add(series);
    
    initialNumberStart = 1;
    initialNumberEnd = data.size() - 1;

    chartContainer.getChildren().add(lineChart);
    
    HBox.setHgrow(lineChart, Priority.ALWAYS);
  }

  /**
   * Filters the nodes and returns the node x positions within the firstX and lastX positions.
   * 
   * @param firstX The first x position
   * @param lastX The last x position
   * @return The x positions for the nodes within the firstX and lastX
   */
  private List<Double> filterSeries(double firstX, double lastX) {
    List<Double> nodeXPositions = new ArrayList<>();

    lineChart.getData().get(0).getData().forEach(node -> {

      double nodeXPosition = lineChart.getXAxis().getDisplayPosition(node.getXValue());

      if (nodeXPosition >= firstX && nodeXPosition <= lastX) {
        nodeXPositions.add(Double.parseDouble(node.getXValue().toString()));
      }
    });
    
    return nodeXPositions;
  }

}
