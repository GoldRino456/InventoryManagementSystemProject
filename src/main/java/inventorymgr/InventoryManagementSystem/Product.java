package inventorymgr.InventoryManagementSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;


/** Product Object Class.
 *
 * FUTURE ENHANCEMENT: Add manufacturing cost property (calculated off of total cost of all associated parts)
 */
public class Product {

    /** List of all parts used in Product
     */
    private ObservableList<Part> associatedParts;

    /** Unique ID Number
     */
    private int id;

    /** Product Name
     */
    private String name;

    /** Price of Product
     */
    private double price;

    /** Current Inventory of Product
     */
    private int stock;

    /** Minimum Inventory of Product
     */
    private int min;

    /** Maximum Inventory of Product
     */
    private int max;

    /** Constructs a new Product Object.
     *
     * @param id - Product's Unique ID Number
     * @param name - Name of Product
     * @param price - Cost of Product
     * @param stock - Amount of Product In Stock
     * @param min - Minimum Stock Amount of Product
     * @param max - Maximum Stock Amount of Product
     */
    public Product(int id, String name, double price, int stock,
                        int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Sets the ID Value
     *
     * @param id - Product's unique ID Number
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /** Sets the Product Name
     *
     * @param name - Name of Product
     */
    public void setName(String name)
    {
        this.name = name; }

    /** Sets the Product Price
     *
     * @param price - Cost of Product
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /** Sets the current Product Inventory
     *
     * @param stock - Amount of Product In Stock
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /** Sets the Minimum Product Inventory
     *
     * @param min - Min Number of Product
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /** Sets the Maximum Product Inventory
     *
     * @param max - Max Number of Product
     */
    public void setMax(int max)
    {
        this.max = max;
    }

    /** Gets the ID Value
     *
     * @return int - ID of Product
     */
    public int getId()
    {
        return id;
    }

    /** Gets the Product Name
     *
     * @return String - Name of Product
     */
    public String getName()
    {
        return name;
    }

    /** Gets the Product Price
     *
     * @return double - Cost of Product
     */
    public double getPrice()
    {
        return price;
    }

    /** Gets the Current Product Inventory
     *
     * @return int - Stock of Product
     */
    public int getStock()
    {
        return stock;
    }

    /** Gets the Minimum Product Inventory
     *
     * @return int - Min Number of Product
     */
    public int getMin()
    {
        return min;
    }

    /** Gets the Maximum Product Inventory
     *
     * @return int - Max Number of Product
     */
    public int getMax()
    {
        return max;
    }

    /** Adds a selected part into the associatedParts List.
     *
     * @param part - The Part to be added to the Product
     */
    public void addAssociatedPart(Part part)
    {
        if(associatedParts != null)
        {
            associatedParts.add(part);
        }
        else
        {
            List<Part> temp = new ArrayList<Part>();
            associatedParts = FXCollections.observableList(temp);
            associatedParts.add(part);
        }

    }

    /** Removes a selected part from the associatedParts List.
     *
     * @param selectedAssociatedPart - The Part to be removed from the Product
     * @return boolean - True if part was removed, false otherwise.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /** Gets the associatedParts List for the Product.
     *
     * @return ObservableList - Entire associatedParts List.
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }
}


