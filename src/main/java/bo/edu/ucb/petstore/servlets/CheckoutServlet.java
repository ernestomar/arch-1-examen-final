package bo.edu.ucb.petstore.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<PetListServlet.Pet> cart = (List<PetListServlet.Pet>) session.getAttribute("cart");

        if (cart != null && !cart.isEmpty()) {
            try (Connection conn = PetListServlet.getConnection()) {
                conn.setAutoCommit(false);

                PreparedStatement updatePetStmt = conn.prepareStatement("UPDATE pet SET available = false WHERE id = ?");
                PreparedStatement insertTransactionStmt = conn.prepareStatement("INSERT INTO transaction (pet_id) VALUES (?)");

                for (PetListServlet.Pet pet : cart) {
                    updatePetStmt.setLong(1, pet.getId());
                    updatePetStmt.addBatch();

                    insertTransactionStmt.setLong(1, pet.getId());
                    insertTransactionStmt.addBatch();
                }

                updatePetStmt.executeBatch();
                insertTransactionStmt.executeBatch();

                conn.commit();

                session.removeAttribute("cart");

                resp.setContentType("text/html");
                PrintWriter out = resp.getWriter();
                out.println("<html><body>");
                out.println("<h1>Checkout Successful!</h1>");
                out.println("<p>Your purchase has been completed.</p>");
                out.println("<a href=\"./pets\">Back to Pet List</a>");
                out.println("</body></html>");

            } catch (SQLException e) {
                e.printStackTrace();
                // Manejo de errores, rollback, etc.
            }
        } else {
            resp.sendRedirect("./pets");
        }
    }
}
