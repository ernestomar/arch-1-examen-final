package bo.edu.ucb.petstore.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PetListServlet extends HttpServlet {

    // Inner class for Pet model
    public static class Pet {
        private Long id;
        private String name;
        private String species;
        private int age;
        private double price;
        private boolean available;

        public Pet() {
        }

        public Pet(Long id, String name, String species, int age, double price, boolean available) {
            this.id = id;
            this.name = name;
            this.species = species;
            this.age = age;
            this.price = price;
            this.available = available;
        }

        // Getters y Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSpecies() {
            return species;
        }

        public void setSpecies(String species) {
            this.species = species;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }
    }

    // Static methods from DatabaseManager
    private static final String DB_URL = "jdbc:h2:mem:petstore;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void initializeDatabase() {
        try {
            Class.forName("org.h2.Driver");
            try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
                // Crear tabla de mascotas
                stmt.execute("CREATE TABLE IF NOT EXISTS pet (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(255)," +
                        "species VARCHAR(255)," +
                        "age INT," +
                        "price DOUBLE," +
                        "available BOOLEAN)");

                // Crear tabla de transacciones
                stmt.execute("CREATE TABLE IF NOT EXISTS transaction (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "pet_id INT," +
                        "purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (pet_id) REFERENCES pet(id))");

                // Poblar con datos iniciales
                stmt.execute("INSERT INTO pet (name, species, age, price, available) VALUES " +
                        "('Max', 'Dog', 3, 150.00, true)," +
                        "('Lucy', 'Cat', 2, 100.00, true)," +
                        "('Charlie', 'Dog', 5, 200.00, true)," +
                        "('Bella', 'Cat', 1, 120.00, true)," +
                        "('Rocky', 'Dog', 4, 180.00, true)," +
                        "('Molly', 'Cat', 3, 130.00, true)," +
                        "('Buddy', 'Dog', 2, 160.00, true)," +
                        "('Daisy', 'Cat', 4, 110.00, true)," +
                        "('Jack', 'Dog', 6, 220.00, true)," +
                        "('Chloe', 'Cat', 2, 140.00, true)," +
                        "('Toby', 'Dog', 1, 140.00, true)," +
                        "('Sophie', 'Cat', 5, 100.00, true)," +
                        "('Cody', 'Dog', 3, 170.00, true)," +
                        "('Zoe', 'Cat', 2, 125.00, true)," +
                        "('Buster', 'Dog', 7, 250.00, true)," +
                        "('Gracie', 'Cat', 1, 135.00, true)," +
                        "('Duke', 'Dog', 4, 190.00, true)," +
                        "('Mia', 'Cat', 3, 115.00, true)," +
                        "('Riley', 'Dog', 2, 155.00, true)," +
                        "('Lola', 'Cat', 4, 105.00, true)");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head><title>Pet Store</title></head>");
        out.println("<body>");
        out.println("<h1>Welcome to the UCB Pet Store</h1>");
        out.println("<a href=\"./cart\">View Cart</a> | <a href=\"./history\">View Transaction History</a>");
        out.println("<h2>Available Pets</h2>");
        out.println("<table border=\"1\">");
        out.println("<tr><th>Name</th><th>Species</th><th>Age</th><th>Price</th><th>Action</th></tr>");

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM pet WHERE available = true");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("species") + "</td>");
                out.println("<td>" + rs.getInt("age") + "</td>");
                out.println("<td>" + rs.getDouble("price") + "</td>");
                out.println("<td><a href=\"./add-to-cart?id=" + rs.getLong("id") + ">Add to Cart</a></td>");
                out.println("</tr>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error retrieving pets from the database.</p>");
        }

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}