package Software1.Model;

/**
 * @author Cristian Sotomayor
 */

/**
 * OutSourcedPart Class
 */

public class OutSourcedPart extends Part {
    /**
     * Class attribute
     */
    private String companyName;

    /**
     * Class constructor
     *
     * @param partId      int
     * @param name        String
     * @param price       double
     * @param stock       int
     * @param minimum     int
     * @param maximum     int
     * @param companyName String
     */
    public OutSourcedPart(int partId, String name, double price, int stock, int minimum, int maximum, String companyName) {
        super(partId, name, price, stock, minimum, maximum);

        this.companyName = companyName;
    }

    /**
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
