package chart.main;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
  private VBox sliderContainer;

  @FXML
  private Label lowRangeLabel;

  @FXML
  private HBox sliderLowRangeContainer;

  @FXML
  private HBox sliderHighRangeContainer;

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
    
    lineChart.setOnMousePressed(mousePressedEvent -> {
      System.out.println("pressed!");
      xPositions.add(xAxis.getValueForDisplay(mousePressedEvent.getX()));
    });
    
    lineChart.setOnMouseReleased(mouseReleasedEvent -> {
      System.out.println("released!");
      xPositions.add(xAxis.getValueForDisplay(mouseReleasedEvent.getX()));
      
      System.out.println(xPositions);
    });

    createSliders(seriesRange);
    chartContainer.getChildren().add(lineChart);
  }

  /**
   * Creates the high and low sliders.
   * 
   * @param seriesRange The series range
   */
  private void createSliders(List<Integer> seriesRange) {
    System.out.println(seriesRange);
    int min = seriesRange.get(0);
    int max = seriesRange.get(seriesRange.size() - 1);

    Slider sliderLowRange = new Slider();
    sliderLowRange.setValue(min);
    sliderLowRange.setMin(min);
    sliderLowRange.setMax(max);
    sliderLowRange.setShowTickLabels(true);
    sliderLowRange.setShowTickMarks(true);
    sliderLowRange.setSnapToTicks(true);
    sliderLowRange.setMajorTickUnit(10);

    Slider sliderHighRange = new Slider();
    sliderHighRange.setValue(max);
    sliderHighRange.setMin(min);
    sliderHighRange.setMax(max);
    sliderHighRange.setShowTickLabels(true);
    sliderHighRange.setShowTickMarks(true);
    sliderHighRange.setSnapToTicks(true);
    sliderHighRange.setMajorTickUnit(10);

    lowRangeLabel.setText(Double.toString(sliderLowRange.getValue()));
    highRangeLabel.setText(Double.toString(sliderHighRange.getValue()));

    sliderLowRange.valueProperty().addListener(e -> {
      System.out.println(sliderLowRange.getValue());
      lowRangeLabel.setText(Double.toString(sliderLowRange.getValue()));
    });

    sliderHighRange.valueProperty().addListener(e -> {
      highRangeLabel.setText(Double.toString(sliderHighRange.getValue()));
    });

    sliderLowRangeContainer.getChildren().add(sliderLowRange);
    sliderHighRangeContainer.getChildren().add(sliderHighRange);

    HBox.setHgrow(sliderLowRange, Priority.ALWAYS);
    HBox.setHgrow(sliderLowRangeContainer, Priority.ALWAYS);
    HBox.setHgrow(sliderHighRange, Priority.ALWAYS);
    HBox.setHgrow(sliderHighRangeContainer, Priority.ALWAYS);
  }

}
