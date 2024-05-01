package orderItem;

import java.io.Serializable;

/**
 * MovieOrder class contains 2 methods used by client and servers
 * @author HUGHEN FLINT 12177330
 */
public class MovieOrder implements Task, Serializable {
    //intilise values
    private double quantity;
    private double unitPrice;
    private double tax;
    private double totalBill;

    //constructors
    public MovieOrder(double quantity, double unitPrice) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    //getters and setters
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    //sets values to format to 2 decimal places and returns String
    @Override
    public String getResult() {
        return String.format("Number of Movies:%.2f      Price:$%.2f     Tax:$%.2f    Bill Total for Movie:$%.2f", quantity, unitPrice, tax, totalBill);
    }

    //calculates tax and total amount for purchase
    @Override
    public void executeTask() {
        final double COST_PER_MOVIE = 0.30;

        double combined = unitPrice * quantity;
        tax = COST_PER_MOVIE * combined;
        totalBill = tax + combined;
    }
}
