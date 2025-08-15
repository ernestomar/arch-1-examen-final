package bo.edu.ucb.petstore.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        PetListServlet.initializeDatabase();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // No es necesario hacer nada al destruir el contexto
    }
}
