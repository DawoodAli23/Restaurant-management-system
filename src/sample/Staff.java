package sample;

import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Staff {
    private int ID;
    private String fullName;
    private String password;
    private Button btn;
    private Button delete;

    public Staff(int ID, String fullName, String password, Button btn, Button delete) throws IOException {
        this.ID = ID;
        this.fullName = fullName;
        this.password = password;
        this.btn = btn;
        this.delete = delete;
        delete.setOnAction(e->{
//            System.out.println(this.getID());
            if(this.getID()==1){
                AlertBox.display("DENIED","CANNOT DELETE THE ROOT USER");
            }else{
//                System.out.println("else");
                DatabaseConnection dbc = new DatabaseConnection();
                Connection connectDB = dbc.getConnection();
                String query = "delete FROM adminAcc WHERE id=?";
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
                        AlertBox.OKMESSAGE("SUCCESS","USER DELETED");
                    }
                } catch (SQLException | InterruptedException throwable) {
                    System.out.println(throwable.getMessage());
                    throwable.printStackTrace();
                }
            }
        });
        btn.setOnAction(e-> {
//            dashboardController dbc = new dashboardController();
//            System.out.println(dbc.list.get(dbc.list.indexOf(this)));
//            System.out.println(this.getID());
            DatabaseConnection dbc = new DatabaseConnection();
            Connection connectDB = dbc.getConnection();

            String query = "UPDATE adminAcc SET fullName=? ,passcode=? WHERE ID=?";
            PreparedStatement ps = null;
            try {
                ps = connectDB.prepareStatement(query);
                ps.setString(1,this.getFullName());
                ps.setString(2,this.getPassword());
                ps.setInt(3,this.getID());

                if(this.password.length()>=3 && this.fullName.length()>=3){
                    int rs = ps.executeUpdate();
//                System.out.println(rs);
                    System.out.println();
                    if(rs==1){
                        AlertBox.OKMESSAGE("USER UPDATED","USER HAS BEEN UPDATED successfully".toUpperCase());
                    }else{
                        AlertBox.display("UNSUCCESSFUL","USER WITH SIMILAR NAME ALREADY EXIST");
                    }
                }else{
                    AlertBox.display("UNSUCCESSFUL","USERNAME AND PASSWORD SHOULD BE LONGER THEN 3 LETTERS");
                }

            } catch (SQLException | InterruptedException throwable) {
                throwable.printStackTrace();
            }

        });
    }


    public int getID() {
        return ID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public Button getBtn() {
        return btn;
    }
    public Button getDelete(){return delete;}
    public void setFullName(String n){this.fullName=n;}
    public void setPssword(String n){this.password=n;}
//    public void setFullName(String n){this.fullName=n;}
}
