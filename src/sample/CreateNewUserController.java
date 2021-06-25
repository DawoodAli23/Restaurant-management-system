package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CreateNewUserController {
    @FXML
    private TextField newUserName;

    @FXML
    private TextField newPassword;

    @FXML
    private TextField newConfirmPassword;

    @FXML
    void createNewUser(ActionEvent event) throws SQLException {
        if(checkLength(newUserName.getText()) || checkLength(newPassword.getText())  || checkLength(newConfirmPassword.getText())){
            AlertBox.display("ERROR","NO, USER/PASSWORD CAN BE LESS THEN 3 WORDS");
        }else if(!newPassword.getText().equals(newConfirmPassword.getText())){
            AlertBox.display("ERROR","Password doesn't match, Please Try Again.");
        }else{
            DatabaseConnection dbc = new DatabaseConnection();
            Connection connectDB = dbc.getConnection();


            String query = "INSERT INTO adminAcc(fullName,passcode,isSuper) VALUES(?,?,?)";
            PreparedStatement ps = connectDB.prepareStatement(query);
            ps.setString(1,newUserName.getText());
            ps.setString(2,newPassword.getText());
            ps.setBoolean(3,false);
            try{
                ps.executeUpdate();

                AlertBox.confirmationDisplay("success".toUpperCase(),"USER CREATED successfully".toLowerCase(), (Stage) ((Node)event.getTarget()).getScene().getWindow());



            }catch (Exception e){
                System.out.println(e.getMessage());
                AlertBox.display("ERROR","ANOTHER USER EXIST WITH THE SAME NAME");

            }
        }
    }

    private boolean checkLength(String str){
        if(str.length()>=3){
            return false;
        }
        return true;
    }

}
