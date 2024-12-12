package org.example.examservlet.dao;

import org.example.examservlet.entity.PlayerIndex;
import org.example.examservlet.untils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PlayerIndexDAO {

    public List<PlayerIndex> getPlayerIndex() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from PlayerIndex", PlayerIndex.class).list();
        }
    }

    public void savePlayerIndex(PlayerIndex playerIndex) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(playerIndex);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updatePlayerIndex(PlayerIndex playerIndex) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            PlayerIndex existingPlayerIndex = session.get(PlayerIndex.class, playerIndex.getPlayer().getPlayerId());
            if (existingPlayerIndex != null) {
                existingPlayerIndex.setValue(playerIndex.getValue());
                existingPlayerIndex.setIndexer(playerIndex.getIndexer());
                existingPlayerIndex.setPlayer(playerIndex.getPlayer());
                session.update(existingPlayerIndex);
            } else {
                throw new IllegalArgumentException("PlayerIndex isn't exist");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    public void deletePlayerIndex(int playerIndexId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            PlayerIndex playerIndex = session.get(PlayerIndex.class, playerIndexId);
            session.delete(playerIndex);
            if (playerIndex != null) {
                session.delete(playerIndex);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public PlayerIndex getPlayerIndex(int playerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(PlayerIndex.class, playerId);
        }
    }
}
