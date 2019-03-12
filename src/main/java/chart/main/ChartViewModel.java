package chart.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The ChartViewModel.
 */
public class ChartViewModel {

  private List<Integer> data;
  private Random random;

  /**
   * The constructor.
   */
  public ChartViewModel() {
    data = new ArrayList<>();
    random = new Random();

    createRandomData();
  }

  /**
   * Creates the random data for a series.
   */
  private void createRandomData() {
    int size = 50;

    for (int i = 0; i < size; i++) {
      data.add(random.nextInt(size) + 1);
    }

  }

  /**
   * Gets the data for the chart.
   * 
   * @return The data.
   */
  public List<Integer> getData() {
    return data;
  }

}
