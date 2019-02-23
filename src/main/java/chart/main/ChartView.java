package chart.main;

import javafx.fxml.FXML;
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
  private ListView<?> itemsListView;

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

  }

}
