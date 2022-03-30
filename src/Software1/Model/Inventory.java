package Software1.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Cristian Sotomayor
 */

/**
 * Inventory Class
 */

public class Inventory {

    /**
     * ObservableList of type Part containing all parts in application
     */
    private static final ObservableList<Part> allPartsList = FXCollections.observableArrayList();

    /**
     * ObservableList of type Product containing all products in application
     */
    private static final ObservableList<Product> allProductsList = FXCollections.observableArrayList();

    /**
     * Method to add a new part to part list "allPartsList"
     *
     * @param newPart the new part to be added to allPartsList
     */
    public static void addPart(Part newPart) {
        allPartsList.add(newPart);
    }

    /**
     * Method to add a new product to part list "allProductsList"
     *
     * @param newProduct the new product to be added to "allProductsList"
     */
    public static void addProduct(Product newProduct) {
        allProductsList.add(newProduct);
    }

    /**
     * Method that retrieves a part in "allPartsList" with the part id.
     *
     * @param partId the id of the part to be retrieved.
     * @return retrievedPart the part ot be retrieved.
     */
    public static Part lookupPartById(int partId) {

        Part retrievedPart = null;

        for (Part part : allPartsList) {
            if (partId == part.getId()) {
                retrievedPart = part;
            }
        }
        return retrievedPart;
    }

    /**
     * Method that retrieves a products in "allProductsList" with the part id.
     *
     * @param productId the id of the product to be retrieved.
     * @return retrievedProduct the product ot be retrieved.
     */
    public static Product lookupProductById(int productId) {

        Product retrievedProduct = null;

        for (Product product : allProductsList) {
            if (productId == product.getId()) {
                retrievedProduct = product;
            }
        }
        return retrievedProduct;
    }

    /**
     * Method that retrieves a list of parts matching the user input
     *
     * @param retrievedPartName user input to match againsts allPartsList
     * @return retrievedPartList List of all parts that match user input
     */
    public static ObservableList lookupPartByName(String retrievedPartName) {
        System.out.print(retrievedPartName);
        ObservableList<Part> retrievedPartList = FXCollections.observableArrayList();

        if (retrievedPartName.length() != 0) {
            for (Part retrievedPart : allPartsList) {
                if (retrievedPart.getName().toLowerCase().contains(retrievedPartName.toLowerCase())) {
                    retrievedPartList.add(retrievedPart);
                    System.out.print(retrievedPart.getName());
                }
            }

        } else {
            retrievedPartList = allPartsList;
        }

        return retrievedPartList;
    }

    /**
     * Method that retrieves a list of products matching the user input
     *
     * @param productName user input to match againsts allProductsList
     * @return retrievedProductsList List of all products that match user input
     */
    public static ObservableList lookupProductByName(String productName) {
        System.out.print(productName);
        ObservableList<Product> retrievedProductList = FXCollections.observableArrayList();

        if (productName.length() != 0) {
            for (Product retrievedProduct : allProductsList) {
                if (retrievedProduct.getName().toLowerCase().contains(productName.toLowerCase())) {
                    retrievedProductList.add(retrievedProduct);
                    System.out.print(retrievedProduct.getName());
                }
            }

        } else {
            retrievedProductList = allProductsList;
        }

        return retrievedProductList;
    }

    /**
     * Method update AllPartsList with the selected part
     *
     * @param index        the index location
     * @param selectedPart the new product to be added to allProductsList
     */
    public static void updatePart(int index, Part selectedPart) {
        allPartsList.set(index, selectedPart);
    }

    /**
     * Method to update the product list with the selected product
     *
     * @param index      the index location
     * @param newProduct the new product to be added to allProductsList
     */
    public static void updateProduct(int index, Product newProduct) {
        allProductsList.set(index, newProduct);
    }

    /**
     * Method to delete selected part from AllPartsList
     *
     * @param selectedPart the part to be deleted from AllPartsList
     * @return true boolean
     */
    public static boolean deletePart(Part selectedPart) {
        allPartsList.remove(selectedPart);
        return true;
    }

    /**
     * Method to delete selected part from AllProductsList
     *
     * @param selectedProduct the part to be deleted from AllProductsList
     * @return boolean
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProductsList.remove(selectedProduct);
        return true;
    }

    /**
     * Method to return all parts contained in AllPartsList
     *
     * @return AllPartsList List containing all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allPartsList;
    }

    /**
     * Method to return all products contained in AllProductsList
     *
     * @return AllProducts List containing all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProductsList;
    }
}