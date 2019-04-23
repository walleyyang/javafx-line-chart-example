package chart.main;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
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
  private List<Integer> data;
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
        int size = numbers.size();
        double numberStart = size > minSize ? numbers.get(0) : initialNumberStart;
        double numberEnd = numbers.size() > minSize ? numbers.get(size - 1) : initialNumberEnd;

        if (size > minSize) {
          lineChart.addVerticalRangeLines(new Data<>(numberStart, numberEnd));
        }

        lineChart.setOnMouseReleased(released -> {
          if (mouseDragged) {
            initialNumberStart = numberStart;
            initialNumberEnd = numberEnd;
            mouseDragged = false;

            redrawChart();
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

    lineChart = new CustomLineChart<>(xAxis, yAxis);

    data = chartViewModel.getData();

    createSeries(data);
    lineChart.getData().add(series);
    // Test
//    lineChart.addHorizontalRangeLines(new Data<>(0, 15));
//    lineChart.addHorizontalRangeLines(new Data<>(0, 25));

    initialNumberStart = 1;
    initialNumberEnd = data.size() - 1;

    chartContainer.getChildren().add(lineChart);

    HBox.setHgrow(lineChart, Priority.ALWAYS);
  }

  /**
   * Creates the series for the line chart.
   * 
   * @param numbers The list of numbers for the series
   */
  private void createSeries(List<Integer> numbers) {
    int size = numbers.size();
    series = new XYChart.Series<>();
    series.setName("Example");

    for (int i = 0; i < size; i++) {
      series.getData().add(new XYChart.Data<Number, Number>(i, numbers.get(i)));
    }
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
  
  /**
   * Updates the series for the chart.
   */
  private void updateSeries() {
    lineChart.getData().remove(0);
    lineChart.getData().add(series);
  }

  /**
   * Redraws the chart.
   */
  private void redrawChart() {
    List<Integer> filteredSeries = new ArrayList<>();

    data.forEach(number -> {
      if (number >= initialNumberStart && number <= initialNumberEnd) {
        filteredSeries.add(number);
      }
    });

    if (!filteredSeries.isEmpty()) {
      createSeries(filteredSeries);
      updateSeries();
      lineChart.removeVerticalRangeLines();
    }
  }

  /**
   * Resets the series for the chart.
   * 
   * @param event The event
   */
  @FXML
  void resetChart(ActionEvent event) {
    createSeries(data);
    updateSeries();
  }

}
