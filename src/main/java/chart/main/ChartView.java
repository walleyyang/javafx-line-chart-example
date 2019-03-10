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
public class ChartView {

  ChartViewModel chartViewModel;

  @FXML
  private VBox mainContainer;

  @FXML
  private Label lowRangeLabel;

  @FXML
  private Label highRangeLabel;

  @FXML
  private HBox chartContainer;

  @FXML
  private HBox controlsContainer;

  @FXML
  private Button addSeriesButton;

  /**
   * The constructor.
   */
  public ChartView() {
    chartViewModel = new ChartViewModel();
  }

  /**
   * The initialize method.
   */
  public void initialize() {
    handleEvents();
    createChart();
  }

  /**
   * Handles the events.
   */
  private void handleEvents() {
    addSeriesButton.setOnAction(e -> chartViewModel.addNewSeries());
  }

  /**
   * Creates the charts.
   */
  private void createChart() {
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    CustomLineChart<Number, Number> lineChart = new CustomLineChart<>(xAxis, yAxis);
    List<Integer> seriesRange = new ArrayList<>();
    List<Number> xPositions = new ArrayList<>();

    HBox.setHgrow(lineChart, Priority.ALWAYS);

    chartViewModel.getData().forEach((k, v) -> {
      for (int i = 0; i < v.size(); i++) {
        series.getData().add(new XYChart.Data<Number, Number>(i, v.get(i)));
        seriesRange.add(i);
      }

      lineChart.getData().add(series);
    });

    lineChart.setOnMousePressed(pressed -> {
      // Get coordinate from the scene and transform to coordinates from the chart axis
      Point2D firstSceneCoordinate = new Point2D(pressed.getSceneX(), pressed.getSceneY());
      double firstX = xAxis.sceneToLocal(firstSceneCoordinate).getX();

      lineChart.setOnMouseDragged(dragged -> lineChart.setOnMouseReleased(released -> {
        Point2D secondSceneCoordinate = new Point2D(released.getSceneX(), pressed.getSceneY());
        double lastX = xAxis.sceneToLocal(secondSceneCoordinate).getX();

        lineChart.addVerticalRangeLines(new Data<>(firstX, lastX));
      }));
    });


    chartContainer.getChildren().add(lineChart);
  }

}
