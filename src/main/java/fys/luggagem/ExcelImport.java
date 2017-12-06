package fys.luggagem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
    private String luggageTag;
    private String locationFound;
    private String primaryColor;
    private String secondaryColor;

    private String luggageSize;
    private String luggageSizeHeigth;
    private String luggageSizeWidth;
    private String luggageSizeDepth;

    private String luggageWeight;

    private String travellerNameAndCity;
    private String travellerNameAndCityName;
    private String travellerNameAndCityCity;

    private String comments;

//    private static int beginAtRow = 0;
//    private static int a = 1;
//    private static String temp;
    public static List<ExcelImport> importFoundLuggageFromExcel(String fileName) throws IOException {
        List<ExcelImport> luggageList = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(fileName);
        Sheet firstSheet = workbook.getSheetAt(0);
        int numOfColumns = firstSheet.getRow(20).getPhysicalNumberOfCells();

//        while (beginAtRow == 0) {
//            
//            Row row = firstSheet.getRow(a);
//            Cell cell = row.getCell(0);
//            
//            setTemp(cellToString(cell));
//            if (temp == "Registration nr") {
//                beginAtRow = a;
//            }
//            a++;
//        }
        for (int i = 4; i < firstSheet.getLastRowNum(); i++) {
            ExcelImport importedLugage = new ExcelImport();
            Row row = firstSheet.getRow(i);
            for (int j = 0; j < numOfColumns; j++) {
                Cell cell = row.getCell(j);
                switch (j) {
                    case 0:
                        importedLugage.setRegistrationNr(cellToString(cell));
                        break;
                    case 1:
//                        importedLugage.setDateFound(cellToString(cell));
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
                        break;
                    case 6:
                        importedLugage.setLuggageTag(cellToString(cell));
                        break;
                    case 7:
                        importedLugage.setLocationFound(cellToString(cell));
                        break;
                    case 8:
                        importedLugage.setPrimaryColor(cellToString(cell));
                        break;
                    case 9:
                        importedLugage.setSecondaryColor(cellToString(cell));
                        break;
                    case 10:
                        importedLugage.setLuggageSize(cellToString(cell));
                        break;
                    case 11:
                        importedLugage.setLuggageWeight(cellToString(cell));
                        break;
                    case 12:
                        importedLugage.setTravellerNameAndCity(cellToString(cell));
                        break;
                    case 13:
                        importedLugage.setComments(cellToString(cell));
                }
            }
            luggageList.add(importedLugage);
        }
        return luggageList;
    }

    public String getLuggageTag() {
        return luggageTag;
    }

    public void setLuggageTag(String num) {
        luggageTag = num;
    }

    public String getLocationFound() {
        return locationFound;
    }

    public void setLocationFound(String num) {
        locationFound = num;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String num) {
        primaryColor = num;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String num) {
        secondaryColor = num;
    }

    public void setLuggageSize(String num) {
        if (num != null) {
            int i = 0;
            String[] parts = new String[3];

            for (String retval : num.split("x")) {
                parts[i] = retval;
                i++;
            }
            String heigth = parts[0];
            String width = parts[1];
            String depth = parts[2];

            luggageSizeHeigth = heigth;
            luggageSizeWidth = width;
            luggageSizeDepth = depth;
        } else {
            luggageSizeHeigth = "";
            luggageSizeWidth = "";
            luggageSizeDepth = "";
        }
    }

    public String getLuggageSizeHeigth() {
        return luggageSizeHeigth;
    }

    public String getLuggageSizeWidth() {
        return luggageSizeWidth;
    }

    public String getLuggageSizeDepth() {
        return luggageSizeDepth;
    }

    public String getLuggageWeight() {
        return luggageWeight;
    }

    public void setLuggageWeight(String num) {
        luggageWeight = num;
    }

    public void setTravellerNameAndCity(String num) {

        if (num != null) {
            int i = 0;
            String[] parts = new String[2];

            for (String retval : num.split(", ")) {
                parts[i] = retval;
                i++;
            }
            String name = parts[0];
            String city = parts[1];

            travellerNameAndCityName = name;
            travellerNameAndCityCity = city;

        } else {
            travellerNameAndCityName = "";
            travellerNameAndCityCity = "";
        }

    }

    public String getTravellerNameAndCityName() {
        return travellerNameAndCityName;
    }

    public String getTravellerNameAndCityCity() {
        return travellerNameAndCityCity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String num) {
        comments = num;
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
        num = num.replace("/", "");
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
