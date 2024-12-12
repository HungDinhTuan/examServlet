package org.example.examservlet.controllers;

import jakarta.servlet.RequestDispatcher;
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
import org.example.examservlet.model.req.PlayerReq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        String action = req.getParameter("action");
        if (action == null || action.isEmpty()) action = "list";
        switch (action) {
            case "list": showListPlayers(req, resp); break;
            case "new": formNewPlayer(req, resp); break;
            case "edit": formEditPlayer(req, resp); break;
            case "delete": deletePlayer(req, resp); break;
        }
    }

    private void showListPlayers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Player> players = playerDAO.getPlayers();
        List<Indexer> indexers = indexerDAO.getIndexers();
        List<PlayerIndex> playerIndexes = playerIndexDAO.getPlayerIndex();
        List<PlayerReq> playerReqList = new ArrayList<>();

        for (Player player : players) {
            for (PlayerIndex playerIndex : playerIndexes) {
                if (playerIndex.getPlayer().getPlayerId() == player.getPlayerId()) {
                    Indexer indexer = playerIndex.getIndexer();
                    PlayerReq playerReq = new PlayerReq();
                    playerReq.setPlayerId(player.getPlayerId());
                    playerReq.setFullName(player.getFullName());
                    playerReq.setAge(player.getAge());
                    playerReq.setIndexName(indexer.getName());
                    playerReq.setValue(playerIndex.getValue());
                    playerReqList.add(playerReq);
                }
            }
        }

        req.setAttribute("playerReqList", playerReqList);

        RequestDispatcher rd = req.getRequestDispatcher("players/list.jsp");
        rd.forward(req, resp);
    }

    private void formNewPlayer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Indexer> indexers = indexerDAO.getIndexers();
        req.setAttribute("indexers", indexers);
        RequestDispatcher rd =  req.getRequestDispatcher("players/create.jsp");
        rd.forward(req, resp);
    }

    private void formEditPlayer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int playerId = Integer.parseInt(req.getParameter("playerId"));
        List<Indexer> indexers = indexerDAO.getIndexers();
        Player player = playerDAO.getPlayer(playerId);
        PlayerIndex playerIndex = playerIndexDAO.getPlayerIndex(playerId);
        Indexer indexer = new Indexer();
        for (Indexer index : indexers) {
            if (Objects.equals(index.getIndexId(), playerIndex.getIndexer().getIndexId())) {
                indexer.setIndexId(index.getIndexId());
                indexer.setName(index.getName());
            }
        }

        req.setAttribute("playerIndex", playerIndex);
        req.setAttribute("player", player);
        req.setAttribute("indexers", indexers);
        req.setAttribute("indexer", indexer);

        RequestDispatcher rd = req.getRequestDispatcher("players/update.jsp");
        rd.forward(req, resp);
    }

    private void deletePlayer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int playerId = Integer.parseInt(req.getParameter("playerId"));
        playerIndexDAO.deletePlayerIndex(playerId);
        playerDAO.deletePlayer(playerId);

        resp.sendRedirect("players?action=list");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "insert": addPlayer(req, resp); break;
            case "update": updatePlayer(req, resp); break;
        }
    }

    private void addPlayer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String fullName = req.getParameter("name");
            int age = Integer.parseInt(req.getParameter("age"));
            int indexId = Integer.parseInt(req.getParameter("indexId"));
            float value = Float.parseFloat(req.getParameter("value"));

            Indexer indexer = new Indexer();
            indexer.setIndexId(indexId);
            Player player = new Player();
            player.setName(fullName);
            player.setFullName(fullName);
            player.setAge(age);
            player.setIndexer(indexer);

            playerDAO.savePlayer(player);

            PlayerIndex playerIndex = new PlayerIndex();
            playerIndex.setPlayer(player);
            playerIndex.setIndexer(indexer);
            playerIndex.setValue(value);

            playerIndexDAO.savePlayerIndex(playerIndex);

            resp.sendRedirect("players?action=list");
        } catch (Exception e) {
            formNewPlayer(req, resp);
        }
    }

    private void updatePlayer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        int indexId = Integer.parseInt(req.getParameter("indexId"));
        float value = Float.parseFloat(req.getParameter("value"));

        Player player = new Player();
        player.setName(fullName);
        player.setFullName(fullName);
        player.setAge(age);

        Indexer indexer = new Indexer();
        indexer.setIndexId(indexId);
        player.setIndexer(indexer);

        playerDAO.updatePlayer(player);

        resp.sendRedirect("players?action=list");
    }
}
