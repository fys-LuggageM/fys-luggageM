package fys.luggagem;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author Tabish N
 */
public class PDFExport {

    public static void makePdf(String filename, String screenName, BufferedImage image) throws IOException {

        try (PDDocument doc = new PDDocument()) {
            PDImageXObject logoImg = PDImageXObject.createFromFile("src/main/resources/images/Naamloos-2.png", doc);
            PDImageXObject pdfScreenshotImage = LosslessFactory.createFromImage(doc, image);

            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(doc, page)) {

                content.drawImage(logoImg, 10, 718);
                content.drawImage(pdfScreenshotImage, 0, 0);

                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 24);

                content.newLineAtOffset(450, 730);

                String screenNamePdf = screenName;
                content.showText(screenNamePdf);
                content.endText();
            }

            doc.save(filename);
        }
    }

}
