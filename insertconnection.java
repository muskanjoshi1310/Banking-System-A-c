import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;


public class insertconnection {
    public static void main(String[]args){
        String url="jdbc:MySQL://localhost:3306/people"; 
        String user="root";
        String password="12345";
        try {
            Connection myconn= DriverManager.getConnection(url,user,password);
            Statement  myStmt = myconn.createStatement();
            String sql= "insert into people" + "(people_id, people_name, people_course)"+"values(8,' smith',' 6 ')";
            String q ="delete into people"+"(people_id,people_name,people_course)"+"values(4,'smith','6')";
            myStmt.executeUpdate(sql);
            System.out.println("Insert complete");
            myconn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
