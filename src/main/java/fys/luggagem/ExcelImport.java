package fys.luggagem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Mees Sour
 */
public class ExcelImport {

    private String registrationNr;
    private String dateFound;
    private String timeFound;
    private String luggageType;
    private String brand;
    private String flightNr;

    public static List<ExcelImport> importFoundLuggageFromExcel(String fileName) throws IOException {
        List<ExcelImport> luggageList = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(fileName);
        Sheet firstSheet = workbook.getSheetAt(0);
        int numOfColumns = firstSheet.getRow(20).getPhysicalNumberOfCells();

        for (int i = 5; i < firstSheet.getLastRowNum(); i++) {
            ExcelImport importedLugage = new ExcelImport();
            Row row = firstSheet.getRow(i);
            for (int j = 0; j < numOfColumns; j++) {
                Cell cell = row.getCell(j);
                switch (j) {
                    case 0:
                        importedLugage.setRegistrationNr(cellToString(cell));
                        break;
                    case 1:
                        break;
                    case 2:
                        importedLugage.setTimeFound(cellToString(cell));
                        break;
                    case 3:
                        importedLugage.setLuggageType(cellToString(cell));
                        break;
                    case 4:
                        importedLugage.setBrand(cellToString(cell));
                        break;
                    case 5:
                        importedLugage.setFlightNr(cellToString(cell));
                }
            }
            luggageList.add(importedLugage);
        }
        return luggageList;
    }

    private static String cellToString(Cell cell) {
        String str = "";
        if (cell == null) {
            str = "";
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            str = NumberToTextConverter.toText(cell.getNumericCellValue());
            if (DateUtil.isCellDateFormatted(cell)) {
                str = cell.toString();
                // if the cell contained a time value, the apache library will not properly convert to string
                if (str.equals("31-dec-1899")) {
                    // compute time in the day in seconds
                    int secsInDay = (int) ((cell.getDateCellValue().getTime() / 1000) % 86400);
                    if (secsInDay < 0) {
                        secsInDay += 86400;
                    }
                    // compute hours, minutes and format the string
                    int hours = secsInDay / 3600;
                    int minutes = (secsInDay % 3600) / 60;
                    str = String.format("%02d:%02d", hours, minutes);
                }
            }
        } else {
            str = cell.toString();
        }
        return str;
    }

    private void setRegistrationNr(String num) {
        registrationNr = num;
    }

    public String getRegistrationNr() {
        return registrationNr;
    }

    private void setDateFound(String num) {
        dateFound = num;
    }

    public String getDateFound() {
        return dateFound;
    }

    private void setTimeFound(String num) {
        timeFound = num;
    }

    public String getTimeFound() {
        return timeFound;
    }

    private void setLuggageType(String num) {
        luggageType = num;
    }

    public String getLuggageType() {
        return luggageType;
    }

    private void setBrand(String num) {
        brand = num;
    }

    public String getBrand() {
        return brand;
    }

    private void setFlightNr(String num) {
        flightNr = num; 
    }
    
    public String getFlightNr() {
        return flightNr;
    }
}
