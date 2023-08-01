package inventorymgr.InventoryManagementSystem;

/**
 * Outsourced Part (Subclass of Part).
 *
 * FUTURE ENHANCEMENT: Add Shipping Cost and Tracking Number Property
 */
public class Outsourced extends Part {

    /** Name of Company Part was purchased from
     */
    private String companyName;

    /** Constructs a new Outsourced Part Object.
     *
     * @param id - Part's Unique ID Number
     * @param name - Name of Part
     * @param price - Cost of Part
     * @param stock - Amount of Part In Stock
     * @param min - Minimum Stock Amount of Part
     * @param max - Maximum Stock Amount of Part
     * @param companyName - Company Part was Purchased From
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** changes the value stored in companyName
     *
     * @param companyName
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /** Returns the companyName
     *
     * @return String - companyName
     */
    public String getCompanyName()
    {
        return companyName;
    }
}
