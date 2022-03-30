package Software1.Model;

/**
 * Supplied class Part.java
 */

/**
 * @author Cristian Sotomayor
 */

public abstract class Part {
    /**
     * class attribute
     */
    private int id;
    /**
     * class attribute
     */
    private String name;
    /**
     * class attribute
     */
    private double price;
    /**
     * class attribute
     */
    private int stock;
    /**
     * class attribute
     */
    private int minimum;
    /**
     * class attribute
     */
    private int maximum;

    /**
     * Class Constructor
     *
     * @param id      int
     * @param name    String
     * @param price   double
     * @param stock   int
     * @param minimum int
     * @param maximum int
     */
    public Part(int id, String name, double price, int stock, int minimum, int maximum) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMinimum() {
        return minimum;
    }

    /**
     * @param minimum the min to set
     */
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    /**
     * @return the max
     */
    public int getMaximum() {
        return maximum;
    }

    /**
     * @param maximum the max to set
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }
}

