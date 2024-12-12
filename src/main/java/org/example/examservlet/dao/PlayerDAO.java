package org.example.examservlet.dao;

import org.example.examservlet.entity.Player;
import org.example.examservlet.untils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PlayerDAO {
    public List<Player> getPlayers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Player", Player.class).list();
        }
    }

    public void savePlayer(Player player) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updatePlayer(Player player) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Player existingPlayer = session.get(Player.class, player.getPlayerId());
            if (existingPlayer != null) {
                existingPlayer.setName(player.getName());
                existingPlayer.setFullName(player.getFullName());
                existingPlayer.setAge(player.getAge());
                existingPlayer.setIndexer(player.getIndexer());
                session.update(existingPlayer);
            } else {
                throw new IllegalArgumentException("Player isn't exist");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    public void deletePlayer(int playerId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Player player = session.get(Player.class, playerId);
            if (player != null) {
                session.delete(player);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Player getPlayer(int playerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Player.class, playerId);
        }
    }
}
