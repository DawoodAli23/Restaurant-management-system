package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class userdashboardController {

    @FXML
    private Button logoutBtn;

    @FXML
    private TextField newNameOfTheDishF;

    @FXML
    private TextArea newDescriptionOfTheDishF;

    @FXML
    private TextField newPriceOfTheDishF;

    @FXML
    private Button insertImgB;

    @FXML
    private Button addItemB;



    @FXML
    private TableView<Dish> TOMenu;

    @FXML
    private TableColumn<Dish, String> TOName;

    @FXML
    private TableColumn<Dish, Double> TOprice;

    @FXML
    private TextField takeOrderTFName;

    @FXML
    private TextField takeOrderTFQtn;

    @FXML
    private Button AddItemBill;

    @FXML
    private TableView<BillItem> BillTable;

    @FXML
    private TableColumn<BillItem, String> BillTableName;

    @FXML
    private TableColumn<BillItem, Integer> BillTableQtn;

    @FXML
    private TableColumn<BillItem, Double> BillTablePrice;


    @FXML
    private TextField totalBillTF;

    @FXML
    private TextField PaidAmountTF;

    @FXML
    private TextField ReturnAmountTF;

    private ObservableList<BillItem> Billlist = FXCollections.observableArrayList();

    private double totalBill;

    @FXML
    public void initialize() throws FileNotFoundException {
//        System.out.println("Hello World");
        this.addEventHanderPaidAmount();
        this.updateDishTable();
        PaidAmountTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    PaidAmountTF.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }

        });
    }



    public void updateDishTable() throws FileNotFoundException {
//        String dImg;
        ObservableList<Dish> list = FXCollections.observableArrayList();
        ObservableList<Dish> list2 = FXCollections.observableArrayList();
        DatabaseConnection dbc = new DatabaseConnection();
        Connection connectDB = dbc.getConnection();
        try{
            String query = "SELECT id,dishName,dishDesc,price,dishImg FROM dish";
            PreparedStatement ps = connectDB.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String dName = rs.getString(2);
                String dDesc = rs.getString(3);
                Float dPrice =  rs.getFloat(4);
                Double ddPrice = Double.parseDouble(dPrice.toString());
                String dImg = rs.getString(5);
                Image photo = new Image(new FileInputStream(dImg));
                ImageView iv = new ImageView(photo);
                iv.setFitWidth(200);
                iv.setPreserveRatio(true);
                Dish dish = new Dish(id,dName,dDesc,ddPrice,iv,new Button("UPDATE"),new Button("DELETE"));
//                Dish dish = new Dish("WALLAH","WALLAH",255.5,"INFO.png");
//                e.getTableView().getItems().get(e.getTablePosition().getRow()).setPssword(e.getNewValue());
//                dish.printAll();
                list.add(dish);
                list2.add(dish);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

//        dishMenu.getItems().clear();
//        dishImg.setCellValueFactory(new PropertyValueFactory<Dish, ImageView>("img"));
//        dishName.setCellValueFactory(new PropertyValueFactory<Dish,String>("name"));
//        dishDesc.setCellValueFactory(new PropertyValueFactory<Dish,String>("desc"));
//        dishPrice.setCellValueFactory(new PropertyValueFactory<Dish,Double>("price"));
//        dEdit.setCellValueFactory(new PropertyValueFactory<Dish,Button>("btn"));
//        dDelete.setCellValueFactory(new PropertyValueFactory<Dish,Button>("delete"));


        TOName.setCellValueFactory(new PropertyValueFactory<Dish,String>("name"));
        TOprice.setCellValueFactory(new PropertyValueFactory<Dish,Double>("price"));


//        dishMenu.setItems(list);
        TOMenu.setItems(list2);
//        System.out.println(dishMenu.getItems().size());
//        System.out.println(TOMenu.getSelectionModel().getSelectedItem().getPrice());



    }

    @FXML
    void deleteSelectedItem(ActionEvent event) {
        ObservableList<BillItem> selectedItem,allProduct;
        allProduct = BillTable.getItems();
        selectedItem = BillTable.getSelectionModel().getSelectedItems();

        totalBill-=(BillTable.getSelectionModel().getSelectedItem().getPrice());
        totalBillTF.setText(totalBill+"");
        if(totalBill==0) totalBillTF.setText("");
        selectedItem.forEach(allProduct::remove);
        if(totalBillTF.getText().length()==0){
            ReturnAmountTF.setText(PaidAmountTF.getText());
        }else{
            ReturnAmountTF.setText(Double.parseDouble(totalBillTF.getText())-Double.parseDouble(PaidAmountTF.getText())+"");
        }
    }

    @FXML
    void TOMenuSelect(MouseEvent event) {
        takeOrderTFName.setText(TOMenu.getSelectionModel().getSelectedItem().getName());
        takeOrderTFQtn.setText("1");
        takeOrderTFQtn.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    takeOrderTFQtn.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        AddItemBill.setOnAction(e->{
//            double bp =
            totalBill += TOMenu.getSelectionModel().getSelectedItem().getPrice()*Double.parseDouble(takeOrderTFQtn.getText());
            BillItem bi = new BillItem(takeOrderTFName.getText(),Integer.parseInt(takeOrderTFQtn.getText()),TOMenu.getSelectionModel().getSelectedItem().getPrice()*Integer.parseInt(takeOrderTFQtn.getText()));
            Billlist.addAll(bi);
//           list.a
            BillTableName.setCellValueFactory(new PropertyValueFactory<BillItem, String>("name"));
            BillTableQtn.setCellValueFactory(new PropertyValueFactory<BillItem, Integer>("qtn"));
            BillTablePrice.setCellValueFactory(new PropertyValueFactory<BillItem, Double>("price"));




            BillTable.setItems(Billlist);
            BillTable.getColumns().get(0).setVisible(false);
            BillTable.getColumns().get(0).setVisible(true);
            BillTable.refresh();
            totalBillTF.setText(String.valueOf(totalBill));
            ReturnAmountTF.setText(Double.parseDouble(totalBillTF.getText())-Double.parseDouble(PaidAmountTF.getText())+"");
        });
    }

    private void addEventHanderPaidAmount(){

        totalBillTF.textProperty().addListener((observable, oldValue, newValue) -> {
            PaidAmountTF.textProperty().addListener((pObservable, pOldValue, pNewValue) -> {
                ReturnAmountTF.setText(((Double.parseDouble(totalBillTF.getText()))-Double.parseDouble(PaidAmountTF.getText()))+"");
            });
        });
    }

    @FXML
    void newGenerateBill(ActionEvent event) throws InterruptedException {
        if(totalBillTF.getText().length()==0 || PaidAmountTF.getText().length()==0 || ReturnAmountTF.getText().length()==0){
            AlertBox.display("ERROR","NO AMOUNT PAID YET");
        }else if(Double.parseDouble(ReturnAmountTF.getText())>0){
            AlertBox.display("ERROR","FULL AMOUNT NOT PAID YET");
        }else{
            System.out.println("PAID");
            Billlist.clear();
            AlertBox.displayBill("BILL",totalBillTF.getText(),PaidAmountTF.getText(),ReturnAmountTF.getText(),Billlist);
            totalBill=0;
            totalBillTF.setText("");
            PaidAmountTF.setText("");
            ReturnAmountTF.setText("");
        }
    }


    @FXML
    void logoutAcc(ActionEvent event) throws IOException {

        Parent loginView = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        Scene loginViewScene = new Scene(loginView);


        //getting info of the parent window
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(loginViewScene);
        window.setScene(new Scene(loginView, 1280 , 720));
        window.show();

    }

    @FXML
    void addNewUser(ActionEvent event) throws IOException {
//        this.refreshRheTable();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createNewUser.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("ADD NEW USER");
        stage.setScene(new Scene(root));
        stage.show();
//        this.
//        this.refreshRheTable();
    }

    @FXML
    void addItemToDatabase(ActionEvent event) throws SQLException {
//        Boolean runNumber = true;
        if(newDescriptionOfTheDishF.getText().equals("") || newNameOfTheDishF.getText().equals("") || insertImgB.getText().equals("INSERT IMAGE (PNG)")){
            AlertBox.display("EMPTY FIELD","NO FIELD CAN BE EMPTY");
        }
        Boolean flag = false;
        Float price=0.0f;
        if(newPriceOfTheDishF.getText().length()>0){
            try{
                price = Float.parseFloat(newPriceOfTheDishF.getText());
                flag=true;
            }catch (NumberFormatException nfe){
                AlertBox.display("NOT A NUMBER","PRICE FIELD CAN ONLY TAKE NUMBERS");
            }
        }
        if(newDescriptionOfTheDishF.getText().equals("") || newNameOfTheDishF.getText().equals("") || insertImgB.getText().equals("INSERT IMAGEget (PNG)")){
            AlertBox.display("EMPTY FIELD","NO FIELD CAN BE EMPTY");
            flag=false;
        }
        if(flag){
            DatabaseConnection dbc = new DatabaseConnection();
            Connection connectDB = dbc.getConnection();


            String query = "INSERT INTO dish(dishName,dishDesc,price,dishImg) VALUES(?,?,?,?)";
            PreparedStatement ps = connectDB.prepareStatement(query);
            ps.setString(1,newNameOfTheDishF.getText());
            ps.setString(2,newDescriptionOfTheDishF.getText());
            ps.setFloat(3,price);
            ps.setString(4,insertImgB.getText());
            try{
                ps.executeUpdate();
                AlertBox.confirmationDisplay("success".toUpperCase(),"DISH ADDED successfully".toLowerCase(), (Stage) ((Node)event.getTarget()).getScene().getWindow(),true);
                newNameOfTheDishF.setText("");
                newDescriptionOfTheDishF.setText("");
                newPriceOfTheDishF.setText("");
                insertImgB.setText("INSERT IMAGE (PNG)");

            }catch (Exception e){
                System.out.println(e.getMessage());
                AlertBox.display("ERROR","SOMETHING WENT WRONG, PLEASE TRY AGAIN.");

            }
        }
    }

    @FXML
    void insertImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(((Node)event.getTarget()).getScene().getWindow());
        if(file!=null){
            String fileChecker = file.getAbsolutePath().toString();
            String extension = fileChecker.substring(fileChecker.length()-3);
            System.out.println(extension);
            if(!extension.equals("png")){
                AlertBox.display("WRONG FORMAT","YOU CAN ONLY INSERT IMAGES OF TYPE PNG");
            }else{
                insertImgB.setText(file.getAbsolutePath());
            }
        }
        Image image = new Image(file.getName());
//        System.out.println(image.toString());

    }

}
