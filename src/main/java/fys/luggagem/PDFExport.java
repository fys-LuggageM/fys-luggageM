package fys.luggagem;

import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author Tabish N
 */
public class PDFExport {

    //Om pdfExport te kunnen gebruiken moet je een "file" aanmaken met behulp van de "selectFileToSave" methode
    //in de MainApp. Deze file doe je daarna in een string met ".getAbsolutePath() erachter. Dit is de eerste parameter.
    //Voor de tweede parameter moet je een string meegeven wat je wilt meegeven als titel van de PDF.
    //De derde parameter is een screenshot van een Anchorpane of iets degelijks. Een screenshot oftewel snapshot maak je
    //door middel van eerst een "WritableImage" te maken van bijv je anchorpane en deze dan om te zetten in een 
    //"BufferedImage". 
    public static void makePdf(String filename, String screenName, BufferedImage image)
            throws IOException {

        try (PDDocument doc = new PDDocument()) {
            PDImageXObject logoImg = PDImageXObject.createFromFile("src/main/resources/images/Naamloos-2.png", doc);
            PDImageXObject pdfScreenshotImage = LosslessFactory.createFromImage(doc, image);

            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(doc, page)) {

                content.drawImage(logoImg, 10, 755, 150, 30);
                content.drawImage(pdfScreenshotImage, 20, 250, 500, 400);

                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 24);

                content.newLineAtOffset(450, 760);

                String screenNamePdf = screenName;
                content.showText(screenNamePdf);
                content.endText();
            }

            doc.save(filename);
        }
    }

    //Gebruik deze methode als je twee screenshots wilt exporteren naar pdf
    public static void makePdfTwoImage(String filename, String screenName, BufferedImage image, BufferedImage image2)
            throws IOException {

        try (PDDocument doc = new PDDocument()) {
            PDImageXObject logoImg = PDImageXObject.createFromFile("src/main/resources/images/Naamloos-2.png", doc);
            PDImageXObject pdfScreenshotImage = LosslessFactory.createFromImage(doc, image);
            PDImageXObject pdfScreenshotImage2 = LosslessFactory.createFromImage(doc, image2);

            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(doc, page)) {

                content.drawImage(logoImg, 10, 755, 150, 30);
                content.drawImage(pdfScreenshotImage, 50, 350, 500, 400);
                content.drawImage(pdfScreenshotImage2, 50, 0, 500, 400);

                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 24);

                content.newLineAtOffset(500, 760);

                String screenNamePdf = screenName;
                content.showText(screenNamePdf);
                content.endText();
            }

            doc.save(filename);
        }
    }

}
