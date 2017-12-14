package fys.luggagem;

import fys.luggagem.models.Data;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Mees Sour
 */
public class ExcelImport {

    private static Data data = MainApp.getData();
    private static MyJDBC myJDBC = MainApp.myJDBC;
    private int registrationNr;
    private static String iata;
    private String dateFound;
    private String timeFound;
    private String luggageType;
    private String brand;
    private String flightNr;
    private String luggageTag;
    private String locationFound;
    private String primaryColor;
    private String secondaryColor;
    private String dateTime;
    private Timestamp ts;

    private String luggageSize;
    private String luggageSizeHeigth;
    private String luggageSizeWidth;
    private String luggageSizeDepth;

    private String luggageWeight;

    private String travellerNameAndCity;
    private String travellerNameAndCityName;
    private String travellerNameAndCityCity;

    private String comments;

    private String labelQuery = "SELECT * FROM luggage WHERE labelnr = ?;";

    public static List<ExcelImport> importFoundLuggageFromExcel(String fileName) throws IOException {
        List<ExcelImport> luggageList = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(fileName);
        int numOfSheets = workbook.getNumberOfSheets();

        //Cycle through the sheets of the workbook
        for (int i = 0; i < numOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            int numOfColumns = sheet.getRow(getDataStartingRow(sheet) - 1).getPhysicalNumberOfCells();
            iata = cellToString(sheet.getRow(1).getCell(1));
            int lastRow = sheet.getPhysicalNumberOfRows() - 1;
            System.out.println(lastRow);

            //Cycle through the rows of sheet i
            for (int j = getDataStartingRow(sheet); j < lastRow; j++) {
                ExcelImport importedLugage = new ExcelImport();
                System.out.println("Row " + j);
                Row row = sheet.getRow(j);

                //Cycle through the columns of row j
                for (int k = 0; k < numOfColumns; k++) {
                    System.out.println("Column " + k);
                    Cell cell = row.getCell(k);
                    switch (k) {
                        case 0:
//                        String regNr = cellToString(cell);
//                        regNr.replace("/", "");
//                        importedLugage.setRegistrationNr(Integer.parseInt(regNr));
                            break;
                        case 1:
                            importedLugage.setDateFound(cellToString(cell));
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
                            break;
                    }
                }

                importedLugage.dateTime = importedLugage.dateFound + " " + importedLugage.timeFound;
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
                sdf.setTimeZone(TimeZone.getTimeZone(data.getTimezone()));
                Date dbDate = null;
                try {
                    dbDate = sdf.parse(importedLugage.dateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cal.setTime(dbDate);
                importedLugage.ts = new Timestamp(cal.getTimeInMillis());

                if (!importedLugage.getLuggageTag().equals("")) {
                    boolean check = true;
                    String labelnr = null;

                    //Search through the database for occurences of labelnr to prevent double data
                    try {
                        PreparedStatement ps = myJDBC.getConnection().prepareStatement(importedLugage.labelQuery);
                        ps.setString(1, importedLugage.getLuggageTag());
                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                            labelnr = rs.getString("labelnr");
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    if (labelnr != null) {
                        check = false;
                    }

                    for (int l = 0; l < luggageList.size(); l++) {
                        if (luggageList.get(l).getLuggageTag().equals(importedLugage.getLuggageTag())) {
                            check = false;
                        }
                    }

                    if (check) {
                        luggageList.add(importedLugage);
                    }
                } else {
                    luggageList.add(importedLugage);
                }
            }
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

    private void setRegistrationNr(int num) {
        registrationNr = num;
    }

    public int getRegistrationNr() {
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

    public String getLuggageSize() {
        return luggageSize;
    }

    public String getIATA() {
        return iata;
    }

    public Timestamp getTimestamp() {
        return ts;
    }

    private static int getDataStartingRow(Sheet sheet) {
        int i = 0;
        boolean isRunning = true;
        while (isRunning) {
            Cell cell = sheet.getRow(i).getCell(10);
            if (cellToString(cell).equals("")) {
                i++;
            } else {
                isRunning = false;
            }
        }

        return i + 1;
    }
}
