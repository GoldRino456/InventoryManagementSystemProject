package inventorymgr.InventoryManagementSystem;

/**
 * InHouse Part (Subclass of Part).
 *
 * FUTURE ENHANCEMENT: Add Manufacturing Time Property
 */
public class InHouse extends Part{

    /** ID Number of Machine Part came from
     */
    private int machineId;

    /** Constructs a new InHouse Part Object.
     *
     * @param id - Part's Unique ID Number
     * @param name - Name of Part
     * @param price - Cost of Part
     * @param stock - Amount of Part In Stock
     * @param min - Minimum Stock Amount of Part
     * @param max - Maximum Stock Amount of Part
     * @param machineId - ID Number of Machine that Part came from
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** changes the value stored in machineID
     *
     * @param machineId
     */
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }

    /** Returns the machineID
     *
     * @return int - machineId
     */
    public int getMachineId()
    {
        return machineId;
    }
}
