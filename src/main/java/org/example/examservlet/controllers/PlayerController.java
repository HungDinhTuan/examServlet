package org.example.examservlet.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.examservlet.dao.IndexerDAO;
import org.example.examservlet.dao.PlayerDAO;
import org.example.examservlet.dao.PlayerIndexDAO;
import org.example.examservlet.entity.Indexer;
import org.example.examservlet.entity.Player;
import org.example.examservlet.entity.PlayerIndex;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PlayerController", value = "/players")
public class PlayerController extends HttpServlet {
    private PlayerIndexDAO playerIndexDAO;
    private PlayerDAO playerDAO;
    private IndexerDAO indexerDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        playerIndexDAO = new PlayerIndexDAO();
        playerDAO = new PlayerDAO();
        indexerDAO = new IndexerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Player> players = playerDAO.getPlayers();
        List<Indexer> indexers = indexerDAO.getIndexers();

        req.setAttribute("players", players);
        req.setAttribute("indexers", indexers);
        req.getRequestDispatcher("players.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            addPlayer(req, resp);
        } else if ("update".equalsIgnoreCase(action)) {
            updatePlayer(req, resp);
        }else if ("delete".equalsIgnoreCase(action)) {
            deletePlayer(req, resp);
        }
    }

    private void addPlayer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String fullName = req.getParameter("fullName");
        String age = req.getParameter("age");
        int indexId = Integer.parseInt(req.getParameter("indexId"));
        float value = Float.parseFloat(req.getParameter("value"));

        Indexer indexer = new Indexer();
        indexer.setIndexId(indexId);
        Player player = new Player();
        player.setName(name);
        player.setFullName(fullName);
        player.setAge(age);
        player.setIndexer(indexer);

        playerDAO.savePlayer(player);

        PlayerIndex playerIndex = new PlayerIndex();
        playerIndex.setPlayer(player);
        playerIndex.setIndexer(indexer);
        playerIndex.setValue(value);

        playerIndexDAO.savePlayerIndex(playerIndex);

        resp.sendRedirect("players.jsp");
    }

    private void updatePlayer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int playerId = Integer.parseInt(req.getParameter("playerId"));
        String name = req.getParameter("name");
        String fullName = req.getParameter("fullName");
        String age = req.getParameter("age");
        int indexId = Integer.parseInt(req.getParameter("indexId"));
        float value = Float.parseFloat(req.getParameter("value"));

        Player player = new Player();
        player.setName(name);
        player.setFullName(fullName);
        player.setAge(age);

        Indexer indexer = new Indexer();
        indexer.setIndexId(indexId);
        player.setIndexer(indexer);

        playerDAO.updatePlayer(player);

        resp.sendRedirect("players.jsp");
    }

    private void deletePlayer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int playerId = Integer.parseInt(req.getParameter("playerId"));
        playerDAO.deletePlayer(playerId);
        resp.sendRedirect("players.jsp");
    }
}
