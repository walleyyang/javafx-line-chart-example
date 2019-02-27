package chart.common.ui.util;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CommonUtils {

  private CommonUtils() {
    // Hide public constructor.
  }

  /**
   * Checks for double clicking.
   * 
   * @param event The mouse event
   * @return The boolean value
   */
  public static boolean doubleClicked(MouseEvent event) {
    return event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2;
  }

}
