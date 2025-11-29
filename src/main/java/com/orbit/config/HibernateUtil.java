package com.orbit.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Membaca konfigurasi dari hibernate.cfg.xml
                Configuration configuration = new Configuration();
                configuration.configure();
                
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Koneksi Database Gagal! Cek hibernate.cfg.xml");
            }
        }
        return sessionFactory;
    }
}