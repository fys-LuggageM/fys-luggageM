package fys.luggagem;

import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author Tabish N
 */
public class PDFExport {

    public static void makePdf(String filename) throws IOException {

        PDDocument doc = new PDDocument();
        PDImageXObject logoImg = PDImageXObject.createFromFile("src/main/resources/images/Naamloos-2.png", doc);

        PDPage page = new PDPage();
        doc.addPage(page);

        PDPageContentStream content = new PDPageContentStream(doc, page);

        content.drawImage(logoImg, 10, 718);

        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 14);
        content.newLineAtOffset(100, 675);
        String test = "Pdf Exporter Test";
        content.showText(test);
        content.endText();

        content.setStrokingColor(Color.black);
        content.addRect(50, 50, (page.getMediaBox().getWidth()) - 100, 600);
        content.stroke();

        content.close();
        doc.save(filename);
        doc.close();
    }
}
