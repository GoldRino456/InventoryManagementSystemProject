package inventorymgr.InventoryManagementSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the inventory lists for the program, as well as part and product list manipulations.
 *
 * FUTURE ENHANCEMENT: Sort List by varying methods; alphabetical, id number, associated parts (for products), etc.
 * FUTURE ENHANCEMENT: name search will create a list regardless of input casing (case-sensitivity should not affect search results).
 */
public class Inventory {

    /** Static list of all Parts in Inventory
     */
    private static ObservableList<Part> allParts;

    /** Static list of all Products in Inventory
     */
    private static ObservableList<Product> allProducts;

    /** Adds a part to the overall parts list. allParts is also initialized here.
     *
     * RUNTIME ERROR: Tried to add part to an uninitialized list. Added logic to check if list has been initialized before adding.
     *
     * @param newPart Part Object (InHouse or Outsourced)
     */
    public static void addPart(Part newPart)
    {
        if(allParts == null)
        {
            List<Part> temp = new ArrayList<Part>();
            allParts = FXCollections.observableList(temp);
        }

        allParts.add(newPart);
    }

    /** Adds a product to the overall product list. allProducts is also initialized here.
     *
     * RUNTIME ERROR: Tried to add product to an uninitialized list. Added logic to check if list has been initialized before adding.
     *
     * @param newProduct Product Object
     */
    public static void addProduct(Product newProduct)
    {
        if(allProducts == null)
        {
            List<Product> temp = new ArrayList<Product>();
            allProducts = FXCollections.observableList(temp);
        }

        allProducts.add(newProduct);
    }

    /** Searches the allParts list for a part with a specific ID number.
     * @return Part - Matching Part Object
     * @param partId int - unique part ID number
     */
    public static Part lookupPart(int partId)
    {
        if(allParts.size() < 1) //If allParts list has less than 1 element
        {
            return null;
        }

        //Search List from Top to Bottom
        for (Part allPart : allParts) {
            if (allPart.getId() == partId) {
                return allPart;
            }
        }

        return null; //No Match Found, return null
    }

    /** Searches the allProducts list for a product with a specific ID number.
     * @return Product - Matching Product Object
     * @param productId int - unique product ID number
     */
    public static Product lookupProduct(int productId)
    {
        if(allProducts.size() < 1) //If allProducts list has less than 1 element
        {
            return null;
        }

        //Search List from Top to Bottom
        for (Product allProduct : allProducts) {
            if (allProduct.getId() == productId) {
                return allProduct;
            }
        }

        return null; //No Match Found, return null
    }

    /** Searches the allParts list for any parts that contain a String in their name.
     * @return ObservableList - List of Parts matching search
     * @param partName String - string value to search
     */
    public static ObservableList<Part> lookupPart(String partName)
    {
        ObservableList<Part> tempObsList = FXCollections.observableList(new ArrayList<Part>());

        for(int i = 0; i < allParts.size(); i++)
        {
            if(allParts.get(i).getName().contains(partName))
            {
                tempObsList.add(allParts.get(i));
            }
        }
        return tempObsList;
    }

    /** Searches the allProducts list for any products that contain a String in their name.
     * @return ObservableList - List of Products matching search
     * @param productName String - string value to search
     */
    public static ObservableList<Product> lookupProduct(String productName)
    {
        ObservableList<Product> tempObsList = FXCollections.observableList(new ArrayList<Product>());

        for(int i = 0; i < allProducts.size(); i++)
        {
            if(allProducts.get(i).getName().contains(productName))
            {
                tempObsList.add(allProducts.get(i));
            }
        }
        return tempObsList;
    }

    /** Updates a selected Part in the allParts list
     * @param index int - allParts index location of Part
     * @param selectedPart Part - Updated Part Information (May be either an InHouse or Outsourced Part)
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }

    /** Updates a selected Product in the allProducts list
     * @param index int - allProducts index location of Product
     * @param newProduct Product - Updated Product Information
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }

    /** Removes a selected Part from the allParts list
     * @return boolean - true if successfully removed, false otherwise.
     * @param selectedPart Part - The Part to be deleted from the list.
     */
    public static boolean deletePart(Part selectedPart)
    {
        return Inventory.getAllParts().remove(selectedPart);
    }

    /** Removes a selected Paroduct from the allProducts list
     * @return boolean - true if successfully removed, false otherwise.
     * @param selectedProduct Product - The Product to be deleted from the list.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return Inventory.getAllProducts().remove(selectedProduct);
    }

    /** Returns the allParts List
     * @return ObservableList - allParts
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /** Returns the allProducts List
     * @return ObservableList - allProducts
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
}
