package com.example.demo1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/conge")
public class CongeServlet extends HttpServlet {

    // Déclaration de la chaîne de connexion, utilisateur et mot de passe
    public static final String DB_URL = "jdbc:mysql://localhost:3306/DBConges?useSSL=false&serverTimezone=UTC";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "root";

    // doGet method for displaying the list of congés
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Conge> congesList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Query to get the list of congés
            String sql = "SELECT * FROM TConge";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            // Iterate over result set and populate congesList
            while (rs.next()) {
                String numero = rs.getString("Numero");
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                int nbj = rs.getInt("NBJ");
                String service = rs.getString("Service");

                congesList.add(new Conge(numero, nom, prenom, nbj, service));
            }

            // Set the congesList as a request attribute to be used in JSP
            request.setAttribute("congesList", congesList);
            // Forward the request to the JSP page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?message=Erreur lors de la récupération des congés.");
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // doPost method for saving a new congé
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve form data
        String numero = request.getParameter("Numero");
        String nom = request.getParameter("Nom");
        String prenom = request.getParameter("Prenom");
        int nbj = Integer.parseInt(request.getParameter("NBJ"));
        String service = request.getParameter("Service");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // SQL query to insert a new congé
            String sql = "INSERT INTO TConge (Numero, Nom, Prenom, NBJ, Service) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, numero);
            stmt.setString(2, nom);
            stmt.setString(3, prenom);
            stmt.setInt(4, nbj);
            stmt.setString(5, service);

            // Execute the query
            int rowsAffected = stmt.executeUpdate();

            // Redirect based on the result
            if (rowsAffected > 0) {
                response.sendRedirect("conge?message=Congé ajouté avec succès!");
            } else {
                response.sendRedirect("conge?message=Erreur lors de l'ajout du congé.");
            }
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            // Handle errors and print stack trace
            e.printStackTrace();
            response.sendRedirect("conge?message=Erreur lors de l'ajout du congé.");
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
