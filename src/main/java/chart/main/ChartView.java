package chart.main;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
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
    LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

    HBox.setHgrow(lineChart, Priority.ALWAYS);

    chartViewModel.getData().forEach((k, v) -> {
      for (int i = 0; i < v.size(); i++) {
        series.getData().add(new XYChart.Data<Number, Number>(i + 1, v.get(i)));
      }

      lineChart.getData().add(series);
    });

    chartContainer.getChildren().add(lineChart);
  }

}
