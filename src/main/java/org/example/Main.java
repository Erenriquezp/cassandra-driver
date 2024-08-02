package org.example;

public class Main {
    public static void main(String[] args) {
        CassandraConnector connector = new CassandraConnector();
        connector.connect("127.0.0.1", 9042, "Tokyo", "ks_results");

        CassandraCRUD crud = new CassandraCRUD(connector.getSession());
        ExcelToCassandra excelUploader = new ExcelToCassandra(crud);

        // Crear tabla si no existe
        connector.getSession().execute("CREATE TABLE IF NOT EXISTS football_results (" +
                "date text, " +
                "home_team text, " +
                "away_team text, " +
                "home_score int, " +
                "away_score int, " +
                "tournament text, " +
                "city text, " +
                "country text, " +
                "neutral text, " +
                "PRIMARY KEY (date, home_team, away_team))");

        // Subir datos desde el archivo de Excel
        excelUploader.uploadExcelData("C:\\Users\\LENOVO\\IntelliJ_IDEA\\Java\\Date_Base\\cassandra-driver\\src\\main\\java\\org\\example\\results.xlsx", "football_results");

        // Leer datos
        crud.read("football_results", "1872-11-30");

        connector.close();
    }
}
