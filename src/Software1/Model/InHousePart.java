package Software1.Model;

/**
 * @author Cristian Sotomayor
 */

/**
 * InHousePart Class
 */

public class InHousePart extends Part {

    /**
     * Variable of type int
     */
    private int machineId;

    /**
     * InHouse Class constructor
     *
     * @param id        class attribute
     * @param name      class attribute
     * @param price     class attribute
     * @param stock     class attribute
     * @param minimum   class attribute
     * @param maximum   class attribute
     * @param machineId class attribute
     */
    public InHousePart(int id, String name, double price, int stock, int minimum, int maximum, int machineId) {
        super(id, name, price, stock, minimum, maximum);

        this.machineId = machineId;
    }

    /**
     * @return the machine id
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the id to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}