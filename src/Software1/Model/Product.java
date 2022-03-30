package Software1.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Cristian Sotomayor
 */

/**
 * Products Class
 */

public class Product {

    /**
     * the list containing all associated parts to a product
     */
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
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
    private int stock;
    /**
     * class attribute
     */
    private double price;
    /**
     * class attribute
     */
    private int minimum;
    /**
     * class attribute
     */
    private int maximum;

    /**
     * Class constructor
     *
     * @param id      int
     * @param name    String
     * @param price   price
     * @param stock   stock
     * @param minimum int
     * @param maximum int
     */
    public Product(int id, String name, double price, int stock, int minimum, int maximum) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    /**
     * Class constructor
     */
    public Product() {
        this(0, null, 0.00, 0, 0, 0);
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

    /**
     * @param part the part to add
     */
    public void addAssociatedPart(ObservableList<Part> part) {
        this.associatedParts.addAll(part);
    }

    /**
     * @param part the part to delete
     */
    public void deleteAssociatedPart(ObservableList<Part> part) {
        this.associatedParts.removeAll(part);
    }

    /**
     * @return associatedPartsList The list to be returned
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
