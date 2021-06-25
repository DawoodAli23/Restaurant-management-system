package sample;
//import com.sun.jdi.connect.spi.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {



    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void loginHandler(ActionEvent event) throws IOException, SQLException {


        DatabaseConnection dbc = new DatabaseConnection();
        Connection connectDB = dbc.getConnection();


        String query = "SELECT * FROM adminAcc WHERE fullName=? AND passcode=?";
        PreparedStatement ps = connectDB.prepareStatement(query);
        ps.setString(1,usernameField.getText());
        ps.setString(2,passwordField.getText());

//        System.out.println(ps);
        try{
//            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            String id,name,pass;
            boolean isSuper;
            boolean qr = rs.next();
            if(qr){
                isSuper=(rs.getBoolean(4));
//                System.out.println("WHAT "+ qr);
//                System.out.println(rs.getString(5));
                this.changeScene(event,isSuper);
                System.out.println(ps.toString());
            }else{
                AlertBox.display("ERROR","USERNAME AND PASSWORD DOESN'T MATCH");
            }


        }catch(Exception e){
//            System.out.println(e.toString());
            System.out.println(e.getMessage());
            AlertBox.display("ERROR","UNABLE TO CHANGE SCENE");
        }
    }

    private void changeScene(ActionEvent event,Boolean isSuper)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            if(isSuper){
                fxmlLoader.setLocation(getClass().getResource("adminDashboard.fxml"));
            }else{
                fxmlLoader.setLocation(getClass().getResource("userDashboard.fxml"));
            }
            Parent dashBoardView = fxmlLoader.load();
            Scene dashboardViewScene = new Scene(dashBoardView);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(dashboardViewScene);
            window.show();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

}
