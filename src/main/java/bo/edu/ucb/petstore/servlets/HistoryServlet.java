package bo.edu.ucb.petstore.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head><title>Transaction History</title></head>");
        out.println("<body>");
        out.println("<h1>Transaction History</h1>");
        out.println("<a href=\"./pets\">Back to Pet List</a>");
        out.println("<table border=\"1\">");
        out.println("<tr><th>Transaction ID</th><th>Pet Name</th><th>Species</th><th>Price</th><th>Purchase Date</th></tr>");

        try (Connection conn = PetListServlet.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT t.id, p.name, p.species, p.price, t.purchase_date FROM transaction t JOIN pet p ON t.pet_id = p.id ORDER BY t.purchase_date DESC");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getLong("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("species") + "</td>");
                out.println("<td>" + rs.getDouble("price") + "</td>");
                out.println("<td>" + rs.getTimestamp("purchase_date") + "</td>");
                out.println("</tr>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error retrieving transaction history.</p>");
        }

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}
