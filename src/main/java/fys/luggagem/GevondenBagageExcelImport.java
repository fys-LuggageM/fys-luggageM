package fys.luggagem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mees Sour
 */
public class GevondenBagageExcelImport {

    private String registrationNumber;
    private String date;
    private String luggageType;
    private String brand;
    
    public static List<GevondenBagageExcelImport> importFoundLuggageFromExcel(String fileName) {
        
        List<GevondenBagageExcelImport> luggageList = new ArrayList<>();
        luggageList.add(new GevondenBagageExcelImport());
        luggageList.add(new GevondenBagageExcelImport());
        
        return luggageList;
    }

}
