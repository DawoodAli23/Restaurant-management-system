package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class AlertBox {
    public static void display(String title,String message){
        Alert alert = new Alert(Alert.AlertType.ERROR,"",ButtonType.OK);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getDialogPane().setPrefSize(400, 100);
        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ButtonType.OK){
//            // ... user chose YES
//        } else {
//            // ... user chose NO or closed the dialog
//        }
    }



    public static void confirmationDisplay(String title, String message, Stage stage) throws InterruptedException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",ButtonType.OK);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getDialogPane().setPrefSize(400, 100);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            stage.close();
        }else{
            alert.wait();
        }

    }

    public static void displayBill(String title, String tb, String pa , String ra, ObservableList<BillItem> dish) throws InterruptedException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",ButtonType.OK);
        alert.setTitle(title);
        String str = "";
        for(BillItem bi:dish){
            String name = (bi.getName());
            String price = (bi.getPrice())+"";
            String temp = name.concat("\t\t\t\t\t\t\t".concat(price))+"\n";
            str+=temp;
//            System.out.println(str);

        }

        str += "\n\n\n\n\nTOTAL BILL \t\t\t\t\t\t\t"+ tb + "\n" +"PAID AMOUNT \t\t\t\t\t\t\t"+pa+"\n"+"RETURN AMOUNT \t\t\t\t\t\t"+ra;
        alert.setContentText(str);
        alert.getDialogPane().setPrefSize(500, 300);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            alert.close();
        }else{
            alert.wait();
        }

    }

    public static void OKMESSAGE(String title, String message) throws InterruptedException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",ButtonType.OK);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getDialogPane().setPrefSize(400, 100);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            alert.close();
        }else{
            alert.wait();
        }

    }

    public static void confirmationDisplay(String title, String message, Stage stage,Boolean flag) throws InterruptedException {    //flag is to only close the alert not the parent window
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",ButtonType.OK);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getDialogPane().setPrefSize(400, 100);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK && flag){
//            stage.close();
            alert.close();
        }else{
            alert.wait();
        }

    }


}
