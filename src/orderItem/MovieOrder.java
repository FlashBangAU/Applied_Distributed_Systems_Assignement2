package orderItem;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class MovieOrder implements Task, Serializable {

    private int quantity;
    private double unitPrice;
    private double tax;
    private double totalBill;

    public MovieOrder() {

    }

    public MovieOrder(int quantity, double unitPrice) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
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

    public String getResult() {
        return "Number of Movies:" + quantity + "  Price:" + unitPrice + " Tax:" + tax + " Bill Total for Book:" + totalBill;
    }

    public void excuteTask() {
        final double COST_PER_MOVIE = 0.30;

        double combined = unitPrice * quantity;
        tax = COST_PER_MOVIE * combined;
        totalBill = tax + combined;
    }
}