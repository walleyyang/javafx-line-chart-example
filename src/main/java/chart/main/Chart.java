package chart.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * The Chart.
 */
public class Chart {

  private UUID id;
  private String title;
  private Random random;
  private List<Integer> data;

  /**
   * The constructor.
   */
  public Chart(String title) {
    this.title = title;

    id = UUID.randomUUID();
    random = new Random();
    data = new ArrayList<>();
    createData();
  }

  /**
   * Creates the data.
   */
  private void createData() {
    int size = 50;

    for (int i = 0; i < size; i++) {
      data.add(random.nextInt(size));
    }
  }

  /**
   * Gets the title.
   * 
   * @return The title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets the id.
   * 
   * @return The id
   */
  public UUID getId() {
    return id;
  }

  /**
   * Gets the data.
   * 
   * @return The data
   */
  public List<Integer> getData() {
    return data;
  }

}
