/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderItem;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class BookOrder implements Task, Serializable {

    private double quantity;
    private double unitPrice;
    private double tax;
    private double totalBill;

    public BookOrder(double quantity, double unitPrice) {
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
        return String.format("Number of Books:%.2f      Price:$%.2f     Tax:$%.2f    Bill Total for Book:$%.2f",quantity,unitPrice,tax,totalBill);
    }

    @Override
    public void executeTask() {
        final double COST_PER_BOOK = 0.10;

        double combined = unitPrice * quantity;
        tax = COST_PER_BOOK * combined;
        totalBill = tax + combined;
    }
}
