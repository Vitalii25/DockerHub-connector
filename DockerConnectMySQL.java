import java.util.Scanner;
import java.sql.*;

public class DockerConnectMySQL {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/bazadanych";
    static final String USER = "vmayuk";
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
                    System.out.println("Connecting...");
                    Thread.sleep(1200);
                }
		   
            } while (!connect);
			System.out.println("Connected");
            stmt = conn.createStatement();
            String sql;

            sql = "DROP TABLE IF EXISTS People";
            stmt.executeUpdate(sql);
		//tworzy tabelę
            sql = "CREATE TABLE People (Id int, Name varchar(255), Surname varchar(255), Age int );";
            stmt.executeUpdate(sql);
		//dodaje do tabeli dane
            sql = "INSERT INTO People(Id, Name, Surname, Age) VALUES (1,'Vitalii', 'Mayuk', '21'),(2, 'One', 'More', '34'),(3, 'Second', 'One', '16');";
            stmt.executeUpdate(sql);
            Scanner menu = new Scanner(System.in);
            String i;
            do {
                System.out.println(""); 
                System.out.println("Choose option");
                System.out.println("[1] add");
                System.out.println("[2] display values");
                System.out.println("[3] exit");
                i = (String) menu.next();
                switch (i) {
                    case "1": {	  
			    //dodanie danych do tabeli
                        Scanner insert = new Scanner(System.in);
                        sql = "SELECT Id FROM People ORDER BY Id DESC LIMIT 1;";
                        ResultSet rs = stmt.executeQuery(sql);
                        int e = 0;
                        if (rs.next()) {
                            e = rs.getInt("Id");
                        }
                        rs.close();
                        e++;
                        sql = "INSERT INTO People (Id, Name, Surname, Age) VALUES (" + e + ",'";
                        System.out.println("Type name:");
                        sql += insert.nextLine();
                        sql += "', '";
                        System.out.println("Type surname:");
                        sql += insert.nextLine();
                        sql += "', '";
                        System.out.println("Type age:");
                        sql += insert.nextLine();
                        sql += "');";
                        stmt.executeUpdate(sql);
						System.out.println("added to table[!]");
                        break;
                    }
                    case "2": {
			    
                        sql = "SELECT Id, Name, Surname, Age FROM People";
                        ResultSet rs = stmt.executeQuery(sql);
                        System.out.printf("|%5s|%15s|%15s|%15s|\n", "Id: ", "Name: ", "Surname: ", "Age: ");
                        while (rs.next()) {
                            int id = rs.getInt("Id");
                            String name = rs.getString("Name");
                            String surname = rs.getString("Surname");
                            String age = rs.getString("Age");
                            System.out.printf("|%4d |%14s |%14s |%14s |\n", id, name, surname, age);
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
                        System.out.println("Choose a right option[!]");
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
