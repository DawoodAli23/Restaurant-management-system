package sample;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Dish {

    private int ID;
    private String name;
    private String desc;
    private double price;
    private ImageView img;
    private Button btn;

    public Button getDelete() {
        return delete;
    }

    private Button delete;
//    private String img;

    public Dish(int ID,String dishName, String dishDesc, double price, ImageView imgAdd,Button btn,Button delete) throws FileNotFoundException {
        this.ID = ID;
        this.name = dishName;
        this.desc = dishDesc;
        this.price = price;
        this.img=imgAdd;
        this.btn=btn;
        this.delete=delete;
        delete.setOnAction(e->{
            DatabaseConnection dbc = new DatabaseConnection();
            Connection connectDB = dbc.getConnection();
            String query = "delete FROM dish WHERE id=?";
            PreparedStatement ps = null;
            try{
//                    System.out.println("try");
                ps=connectDB.prepareStatement(query);
                ps.setInt(1,this.getID());
                int rs = ps.executeUpdate();
//                    System.out.println(rs);
//                    System.out.println(ps.toString());
                if(rs==1){
//                        System.out.println("if");
                    AlertBox.OKMESSAGE("SUCCESS","DISH DELETED");
                }
            } catch (SQLException | InterruptedException throwable) {
                System.out.println(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
        this.btn.setOnAction(e-> {
            DatabaseConnection dbc = new DatabaseConnection();
            Connection connectDB = dbc.getConnection();

            String query = "UPDATE dish SET dishName=?,dishDesc=?,price=? WHERE ID=?";
            PreparedStatement ps = null;
            try {
                ps = connectDB.prepareStatement(query);
                ps.setString(1,this.getName());
                ps.setString(2,this.getDesc());
                ps.setDouble(3,this.getPrice());
                ps.setInt(4,this.getID());

                if(this.name.length()>=3 && this.desc.length()>3){
                    int rs = ps.executeUpdate();
                System.out.println(rs);
                    System.out.println();
                    if(rs==1){
                        AlertBox.OKMESSAGE("DISH UPDATED","DISH HAS BEEN UPDATED successfully".toUpperCase());
                    }else{
                        AlertBox.display("1","UNABLE TO UPDATE THE DISH");
                    }
                }else{
                    AlertBox.display("2","UNABLE TO UPDATE THE DISH,EACH FIELD SHOULD BE LONGER THEN 3 LETTERS");
                }

            } catch (SQLException | InterruptedException throwable) {
                System.out.println(throwable.getMessage());
                AlertBox.display("UNSUCCESSFUL","UNABLE TO UPDATE THE DISH,EACH FIELD SHOULD BE LONGER THEN 3 LETTERS");
            }

        });

    }

    public Dish() {

    }

    void printAll(){
        System.out.print(this.name);
        System.out.print("\t");
        System.out.print(this.desc);
        System.out.print("\t");
        System.out.print(this.price);
        System.out.print("\t");
        System.out.print(this.img);
        System.out.print("\n");
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Button getBtn() {
        return btn;
    }


    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ImageView getImg() {
        return img;
    }

//    public String getImg() {
//        return img;
//    }

//    public void setImg(ImageView add) throws FileNotFoundException {
////        Image img = new Image(add);
////        ImageView imgV = new ImageView();
////        imgV.setImage(img);
//        this.img=add;
//    }
}
