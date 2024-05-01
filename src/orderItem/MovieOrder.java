package orderItem;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class MovieOrder implements Task, Serializable {

    private double quantity;
    private double unitPrice;
    private double tax;
    private double totalBill;

    public MovieOrder(double quantity, double unitPrice) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

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

    @Override
    public String getResult() {
        return String.format("Number of Movies:%.2f      Price:$%.2f     Tax:$%.2f    Bill Total for Movie:$%.2f",quantity,unitPrice,tax,totalBill);
    }

    @Override
    public void executeTask() {
        final double COST_PER_MOVIE = 0.30;

        double combined = unitPrice * quantity;
        tax = COST_PER_MOVIE * combined;
        totalBill = tax + combined;
    }
}