package sample;
import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "SEMESTERPROJECT";
        String databaseUser = "root";
        String databasePassword = "12345678";

        String url = "jdbc:mysql://localhost/" + databaseName;


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch(Exception e){
            System.out.println("ERROR WHILE CONNECTING TO DATABASE");
        }

        return databaseLink;
    }

}
