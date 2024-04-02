/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderItem;

/**
 *
 * @author User
 */
public class MovieOrder {
    int quantity;
    double unitPrice;
    double tax;
    double totalBill;

    public MovieOrder(int quantity, double unitPrice, double tax, double totalBill) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.tax = tax;
        this.totalBill = totalBill;
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

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }
    
    public String excuteTask(){
        
        return null;
    }
    
    public String getResult(){
        
        return null;  
    }
}
