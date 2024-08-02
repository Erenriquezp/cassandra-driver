package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelToCassandra {
    private final CassandraCRUD cassandraCRUD;
    public ExcelToCassandra(CassandraCRUD cassandraCRUD) {
        this.cassandraCRUD = cassandraCRUD;
    }

    public void uploadExcelData(String filePath, String table) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row == null) continue;

                String date = null;
                if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                    date = row.getCell(0).getDateCellValue().toString();
                } else if (row.getCell(0).getCellType() == CellType.STRING) {
                    date = row.getCell(0).getStringCellValue();
                }
                //String date = row.getCell(0).getStringCellValue();
                String home_team = row.getCell(1).getStringCellValue();
                String away_team = row.getCell(2).getStringCellValue();
                int home_score = (int) row.getCell(3).getNumericCellValue();
                int away_score = (int) row.getCell(4).getNumericCellValue();
                String tournament = row.getCell(5).getStringCellValue();
                String city = row.getCell(6).getStringCellValue();
                String country = row.getCell(7).getStringCellValue();
                String neutral = row.getCell(8).getStringCellValue();

                cassandraCRUD.create(table, date, home_team, away_team, home_score, away_score, tournament, city, country, neutral);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
