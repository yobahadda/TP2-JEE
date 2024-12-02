package com.example.demo1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/index")
public class AffichageConge extends HttpServlet {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/DBConges?useSSL=false&serverTimezone=UTC";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "root";

    // Override doGet method to handle GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Conge> congesList = new ArrayList<>();

        // Load MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database driver not found.");
            return;
        }

        // Establish a connection, query data, and populate the congesList
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM TConge")) {

            while (rs.next()) {
                String numero = rs.getString("Numero");
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                int nbj = rs.getInt("NBJ");
                String service = rs.getString("Service");

                congesList.add(new Conge(numero, nom, prenom, nbj, service));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred.");
            return;
        }

        // Set the congesList as a request attribute
        request.setAttribute("congesList", congesList);

        // Forward the request to the index.jsp page
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
