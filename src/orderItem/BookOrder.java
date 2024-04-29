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

    private int quantity;
    private double unitPrice;
    private double tax;
    private double totalBill;

    public BookOrder() {

    }

    public BookOrder(int quantity, double unitPrice) {
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
        return "Number of Books:" + quantity + "  Price:" + unitPrice + " Tax:" + tax + " Bill Total for Book:" + totalBill;
    }

    public void excuteTask() {
        final double COST_PER_BOOK = 0.10;

        double combined = unitPrice * quantity;
        tax = COST_PER_BOOK * combined;
        totalBill = tax + combined;
    }
}
