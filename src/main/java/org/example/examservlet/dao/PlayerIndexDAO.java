package org.example.examservlet.dao;

import org.example.examservlet.entity.PlayerIndex;
import org.example.examservlet.untils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PlayerIndexDAO {
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
}
