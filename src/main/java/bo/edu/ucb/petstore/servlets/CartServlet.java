package bo.edu.ucb.petstore.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head><title>Shopping Cart</title></head>");
        out.println("<body>");
        out.println("<h1>Shopping Cart</h1>");

        HttpSession session = req.getSession();
        List<PetListServlet.Pet> cart = (List<PetListServlet.Pet>) session.getAttribute("cart");

        if (cart != null && !cart.isEmpty()) {
            out.println("<table border=\"1\">");
            out.println("<tr><th>Name</th><th>Species</th><th>Age</th><th>Price</th></tr>");
            double total = 0;
            for (PetListServlet.Pet pet : cart) {
                out.println("<tr>");
                out.println("<td>" + pet.getName() + "</td>");
                out.println("<td>" + pet.getSpecies() + "</td>");
                out.println("<td>" + pet.getAge() + "</td>");
                out.println("<td>" + pet.getPrice() + "</td>");
                out.println("</tr>");
                total += pet.getPrice();
            }
            out.println("</table>");
            out.println("<h2>Total: " + total + "</h2>");
            out.println("<a href=\"./checkout\">Checkout</a>");
        } else {
            out.println("<p>Your cart is empty.</p>");
        }

        out.println("<br/><a href=\"./pets\">Continue Shopping</a>");
        out.println("</body>");
        out.println("</html>");
    }
}
