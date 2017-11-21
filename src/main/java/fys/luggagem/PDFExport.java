package fys.luggagem;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 *
 * @author Tabish N
 */
public class PDFExport {

    public static void makePDF(String filename) throws IOException {
        PDDocument doc = new PDDocument();
        doc.save("C:/pdfbox/document.pdf");
        System.out.println("Export geactiveerd");
        doc.close();
    }
}
