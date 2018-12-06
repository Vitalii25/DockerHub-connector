import java.util.Scanner;
import java.sql.*;

public class DockerConnectMySQL {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/database";
    static final String USER = "VMayuk";
    static final String PASS = "password";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Boolean connect = false;
            do {
                try {
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    connect = true;
                } catch (Exception e) {
                    System.out.println("Connecting with database server");
                    Thread.sleep(1000);
                }
            } while (!connect);
			System.out.println("Connected with database server");
            stmt = conn.createStatement();
            String sql;
            sql = "DROP TABLE IF EXISTS Students";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE Students (StudentId int, FirstName varchar(255), LastName varchar(255));";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO Students(StudentId, FirstName, LastName) VALUES (1,'Vitalii', 'Mayuk'),(2, 'Nick', 'Nickolaskin'),(3, 'John', 'Jonnatan');";
            stmt.executeUpdate(sql);
            Scanner menu = new Scanner(System.in);
            String i;
            do {
                System.out.println("");
                System.out.println("SQL Menu");
                System.out.println("Type number");
                System.out.println("Choose an option:");
                System.out.println("(1) adding");
                System.out.println("(2) display");
                System.out.println("(3) exit");
                i = (String) menu.next();
                switch (i) {
                    case "1": {
                        Scanner insert = new Scanner(System.in);
                        sql = "SELECT StudentId FROM Student ORDER BY StudentId DESC LIMIT 1;";
                        ResultSet rs = stmt.executeQuery(sql);
                        int e = 0;
                        if (rs.next()) {
                            e = rs.getInt("StudentId");
                        }
                        rs.close();
                        e++;
                     sql = "INSERT INTO Students (StudentId, FirstName, LastName) VALUES (" + e + ",'";
                        System.out.println("Type FirstName:");
                        sql += insert.nextLine();
                        sql += "', '";
                        System.out.println("Type LastName:");
                        sql += insert.nextLine();
                        sql += "');";
                        stmt.executeUpdate(sql);
						System.out.println("Work");
                        break;
                    }
                    case "2": {
                        sql = "SELECT StudentId, FirstName, LastName FROM Students";
                        ResultSet rs = stmt.executeQuery(sql);
                     System.out.printf("|%5s|%15s|%15s|\n", "StudentId: ", "FirstName: ", "LastName: ");
                        while (rs.next()) {
                            int id = rs.getInt("StudentId");
                            String first = rs.getString("FirstName");
                            String last = rs.getString("LastName");
                            System.out.printf("|%4d |%14s |%14s |\n", StudentId, FirstName, LastName);
                        }
                        rs.close();
                        break;
                    }
                    case "3": {
                        i = "3";
                        break;
                    }
                    default: {
						System.out.println("");
                        System.out.println("One more time, you shoud win ");
                        break;
                    }
                }
            } while (i != "3");
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
