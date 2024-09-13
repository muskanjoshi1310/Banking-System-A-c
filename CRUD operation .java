import java.sql.Connection;
import java.sql.DriverManager; // Make sure to import this
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class JDBCUtils {
    private static final String URL = "jdbc:mysql://localhost:3306/test_db?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}

public class Implementation {

    public void selectEmployee(int id) {
        String sql = "SELECT id, name, email, country, salary FROM employees WHERE id = ?";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Country: " + rs.getString("country"));
                System.out.println("Salary: " + rs.getDouble("salary"));
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public void insertEmployee(String name, String email, String country, double salary) {
        String sql = "INSERT INTO employees (name, email, country, salary) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, country);
            pstmt.setDouble(4, salary);
            pstmt.executeUpdate();
            System.out.println("Record created.");
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public void updateEmployee(int id, String name, String email, String country, double salary) {
        String sql = "UPDATE employees SET name = ?, email = ?, country = ?, salary = ? WHERE id = ?";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, country);
            pstmt.setDouble(4, salary);
            pstmt.setInt(5, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record updated.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record deleted.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public static void main(String[] args) {
        Implementation obj = new Implementation();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to ABC Limited\nPlease Enter your Choice");
            System.out.println("1. View Employee");
            System.out.println("2. Add New Employee");
            System.out.println("3. Update Existing Employee");
            System.out.println("4. Delete Existing Employee");
            System.out.println("5. Exit");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter Employee ID to view: ");
                    int viewId = sc.nextInt();
                    obj.selectEmployee(viewId);
                    break;
                case 2:
                    System.out.print("Enter Name: ");
                    String name = sc.next();
                    System.out.print("Enter Email: ");
                    String email = sc.next();
                    System.out.print("Enter Country: ");
                    String country = sc.next();
                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();
                    obj.insertEmployee(name, email, country, salary);
                    break;
                case 3:
                    System.out.print("Enter Employee ID to update: ");
                    int updateId = sc.nextInt();
                    System.out.print("Enter New Name: ");
                    String newName = sc.next();
                    System.out.print("Enter New Email: ");
                    String newEmail = sc.next();
                    System.out.print("Enter New Country: ");
                    String newCountry = sc.next();
                    System.out.print("Enter New Salary: ");
                    double newSalary = sc.nextDouble();
                    obj.updateEmployee(updateId, newName, newEmail, newCountry, newSalary);
                    break;
                case 4:
                    System.out.print("Enter Employee ID to delete: ");
                    int deleteId = sc.nextInt();
                    obj.deleteEmployee(deleteId);
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

        sc.close();
    }
}
