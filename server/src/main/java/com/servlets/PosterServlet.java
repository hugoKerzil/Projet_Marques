package com.servlets;

import com.dao.PosterDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.entities.Poster;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/posters/*", "/movies/*"})
public class PosterServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PosterDAO posterDAO = new PosterDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();

        try {
            if ("/posters".equals(servletPath) && pathInfo != null && pathInfo.length() > 1) {
                Long id = Long.parseLong(pathInfo.substring(1));
                Poster poster = posterDAO.findById(id);
                if (poster != null) {
                    response.getWriter().write(objectMapper.writeValueAsString(poster));
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Poster non trouvé");
                }
            } else if ("/movies".equals(servletPath) && pathInfo != null && pathInfo.endsWith("/posters")) {
                String[] parts = pathInfo.split("/");
                Long movieId = Long.parseLong(parts[1]);
                List<Poster> posters = posterDAO.findByMovieId(movieId);
                response.getWriter().write(objectMapper.writeValueAsString(posters));
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Route non reconnue");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Format d'ID invalide");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            Poster newPoster = objectMapper.readValue(request.getReader(), Poster.class);
            posterDAO.save(newPoster);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(objectMapper.writeValueAsString(newPoster));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo != null && pathInfo.length() > 1) {
                Long id = Long.parseLong(pathInfo.substring(1));
                Poster updatedPoster = objectMapper.readValue(request.getReader(), Poster.class);
                updatedPoster.setId(id);
                posterDAO.update(updatedPoster);
                response.getWriter().write("{\"message\": \"Poster mis à jour\"}");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID manquant");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la mise à jour");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo != null && pathInfo.length() > 1) {
                Long id = Long.parseLong(pathInfo.substring(1));
                boolean deleted = posterDAO.delete(id);
                if (deleted) {
                    response.getWriter().write("{\"message\": \"Poster supprimé\"}");
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Poster introuvable");
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID manquant");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la suppression");
        }
    }
}