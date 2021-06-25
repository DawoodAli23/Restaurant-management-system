package sample;

public class BillItem {
    private String name;
    private int qtn;
    private double price;
//    private Button delete;

    public BillItem(String name, int qtn, double price) {
        this.name = name;
        this.qtn = qtn;
        this.price = price;
//        this.delete = delete;
    }

    public String getName() {
        return name;
    }

    public int getQtn() {
        return qtn;
    }

    public double getPrice() {
        return price;
    }

//    public Button getDelete() {
//        return delete;
//    }


}
