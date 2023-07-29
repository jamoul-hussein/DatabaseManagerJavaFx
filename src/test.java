import java.sql.*;

public class test {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String username = "newuser6";
        String password = "password123";
        String database = "Versand";

        try {
            // Connect to the MySQL database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");

            // Verify that the new user can execute a SELECT statement on the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
            stmt = conn.createStatement();
            String selectQuery = "SHOW GRANTS FOR '"+username+"'@'%';";
            rs = stmt.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

            System.out.println("User created and SELECT privilege granted successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing database resources: " + e.getMessage());
            }
        }
    }
}

