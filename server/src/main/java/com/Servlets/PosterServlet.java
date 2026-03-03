package com.Servlets;

import com.DAO.PosterDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.entities.Poster;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/posters/*")
public class PosterServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PosterDAO posterDAO = new PosterDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            if ("getPoster".equals(operation)) {
                Long id = Long.parseLong(request.getParameter("id"));
                Poster poster = posterDAO.findById(id);
                if (poster != null) {
                    response.getWriter().write(objectMapper.writeValueAsString(poster));
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Poster non trouvé");
                }
            } else if ("getMoviePosters".equals(operation)) {
                Long movieId = Long.parseLong(request.getParameter("movieId"));
                List<Poster> posters = posterDAO.findByMovieId(movieId);
                response.getWriter().write(objectMapper.writeValueAsString(posters));
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres invalides");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");
        response.setContentType("application/json");

        try {
            if ("savePoster".equals(operation)) {
                Poster newPoster = objectMapper.readValue(request.getReader(), Poster.class);
                posterDAO.save(newPoster);
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("{\"message\": \"Poster enregistré avec succès\"}");

            } else if ("updatePoster".equals(operation)) {
                Poster updatedPoster = objectMapper.readValue(request.getReader(), Poster.class);
                posterDAO.update(updatedPoster);
                response.getWriter().write("{\"message\": \"Poster mis à jour\"}");

            } else if ("deletePoster".equals(operation)) {
                Long id = Long.parseLong(request.getParameter("id"));
                boolean deleted = posterDAO.delete(id);
                if (deleted) {
                    response.getWriter().write("{\"message\": \"Poster supprimé\"}");
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement");
        }
    }
}