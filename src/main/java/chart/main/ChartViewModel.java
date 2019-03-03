package chart.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The ChartViewModel.
 */
public class ChartViewModel {

  private Map<String, List<Integer>> data;
  private int seriesCounter;
  private Random random;

  /**
   * The constructor.
   */
  public ChartViewModel() {
    data = new HashMap<>();
    seriesCounter = 0;
    random = new Random();

    addNewSeries();
  }

  /**
   * Adds new series to the chart.
   */
  public void addNewSeries() {
    String series = "Series " + seriesCounter++;
    List<Integer> randomData = createRandomData();

    data.put(series, randomData);
  }

  /**
   * Creates the random data for a series.
   * 
   * @return A list of random integer data.
   */
  private List<Integer> createRandomData() {
    int size = 50;
    List<Integer> randomData = new ArrayList<>();

    for (int i = 0; i < size; i++) {
      randomData.add(random.nextInt(size) + 1);
    }

    return randomData;
  }

  /**
   * Gets the data for the chart.
   * 
   * @return The data.
   */
  public Map<String, List<Integer>> getData() {
    return data;
  }

}
