package org.example;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
public class CassandraCRUD {
    private CqlSession session;
    public CassandraCRUD(CqlSession session) {
        this.session = session;
    }
    // Create
    public void create(String table, String date, String home_team, String away_team, int home_score, int away_score, String tournament, String city, String country, String neutral) {
        String query = String.format("INSERT INTO %s (date, home_team, away_team, home_score, away_score, tournament, city, country, neutral) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", table);
        session.execute(SimpleStatement.builder(query)
                .addPositionalValue(date)
                .addPositionalValue(home_team)
                .addPositionalValue(away_team)
                .addPositionalValue(home_score)
                .addPositionalValue(away_score)
                .addPositionalValue(tournament)
                .addPositionalValue(city)
                .addPositionalValue(country)
                .addPositionalValue(neutral)
                .build());
    }

    // Read
    public void read(String table, String date) {
        String query = String.format("SELECT * FROM %s WHERE date = ?", table);
        ResultSet rs = session.execute(SimpleStatement.builder(query)
                .addPositionalValue(date)
                .build());
        for (Row row : rs) {
            System.out.println(row.getString("date") + " " +
                    row.getString("home_team") + " " +
                    row.getString("away_team") + " " +
                    row.getInt("home_score") + " " +
                    row.getInt("away_score") + " " +
                    row.getString("tournament") + " " +
                    row.getString("city") + " " +
                    row.getString("country") + " " +
                    row.getString("neutral"));
        }
    }

    // Update
    public void update(String table, String date, String newCity) {
        String query = String.format("UPDATE %s SET city = ? WHERE date = ?", table);
        session.execute(SimpleStatement.builder(query)
                .addPositionalValue(newCity)
                .addPositionalValue(date)
                .build());
    }

    // Delete
    public void delete(String table, String date) {
        String query = String.format("DELETE FROM %s WHERE date = ?", table);
        session.execute(SimpleStatement.builder(query)
                .addPositionalValue(date)
                .build());
    }
}