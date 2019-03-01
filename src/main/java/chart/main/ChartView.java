package chart.main;

import chart.common.ui.util.CommonUtils;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The ChartView.
 */
public class ChartView {
  
  ChartViewModel chartViewModel;

  @FXML
  private HBox mainContainer;

  @FXML
  private VBox leftContainer;

  @FXML
  private Button clearAllButton;

  @FXML
  private Button selectAllButton;

  @FXML
  private Button syncSelectedButton;

  @FXML
  private Button newItemButton;

  @FXML
  private ScrollPane itemsScrollPane;

  @FXML
  private ListView<String> itemsListView;

  @FXML
  private VBox rightContainer;

  @FXML
  private ScrollPane smallChartsScrollPane;

  @FXML
  private FlowPane smallChartsFlowPane;

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
    bindProperties();
    handleEvents();
  }
  
  /**
   * Contains the bound properties.
   */
  private void bindProperties() {
    itemsListView.itemsProperty().bindBidirectional(chartViewModel.getItemsProperty());
  }
  
  /**
   * Handles the events.
   */
  private void handleEvents() {
    newItemButton.setOnAction(e -> chartViewModel.addNewItem());
    
    itemsListView.setOnMouseClicked(e -> {
      if (CommonUtils.doubleClicked(e)) {
        chartViewModel
          .addNewChart(itemsListView.getSelectionModel().selectedItemProperty().getValue());
      
        createChart();
      }
    });
  }
  
  /**
   * Creates the charts
   */
  private void createChart() {
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    XYChart.Series series = new XYChart.Series<>();
    LineChart lineChart = new LineChart(xAxis, yAxis);
    
    chartViewModel.getChartsProperty().forEach(chart -> {
      chart.getData().forEach(number -> {
        series.getData().add(new XYChart.Data<>(number, number));
      });
      
      lineChart.getData().add(series);
    });
    
    smallChartsFlowPane.getChildren().add(lineChart);
  }

}
