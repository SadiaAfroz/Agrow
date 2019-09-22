package com.example.agrow.model;

public class singleNotification {
    String buyername;
    String addid;
    String orderfrom;
    String amount;
    String cost;

    public singleNotification(String buyername, String addid, String orderfrom, String amount, String cost) {
        this.buyername = buyername;
        this.addid = addid;
        this.orderfrom = orderfrom;
        this.amount = amount;
        this.cost = cost;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }

    public String getAddid() {
        return addid;
    }

    public void setAddid(String addid) {
        this.addid = addid;
    }

    public String getOrderfrom() { return orderfrom;    }

    public void setDivision(String orderfrom) {
        this.orderfrom = orderfrom;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
