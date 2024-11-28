package org.example.examservlet.dao;

import org.example.examservlet.entity.Indexer;
import org.example.examservlet.untils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class IndexerDAO {
    public List<Indexer> getIndexers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Indexer", Indexer.class).list();
        }
    }
}
