// java program to set up connection and get all data from table
import java.lang.*;
import java.sql.*;


class first{
    public static void main(String[]args) throws SQLException{
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/people","root","12345");
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet= statement.executeQuery("select*from people");
            int people_id;
            String people_name;
            String people_course;
            while(resultSet.next()){
                people_id= resultSet.getInt("people_id");
                people_name = resultSet.getString("people_name");
                people_course= resultSet.getString("people_course");
                System.out.println("people_id: " + people_id +  "  title:" + people_name+  "  course:"  +people_course);
            } 
                resultSet.close();
                statement.close();
                connection.close();
            }
 catch(Exception exception) { 
            System.out.println(exception);
        }
    }}


