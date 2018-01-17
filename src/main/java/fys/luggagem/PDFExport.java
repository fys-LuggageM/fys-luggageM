package fys.luggagem;

import fys.luggagem.models.Customer;
import fys.luggagem.models.Luggage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.CCITTFactory;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDInlineImage;

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
            BufferedImage img = ImageIO.read(MainApp.class.getResource("/images/Naamloos-2.png"));
            PDImageXObject logoImg = LosslessFactory.createFromImage(doc, img);
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
            BufferedImage img = ImageIO.read(MainApp.class.getResource("/images/Naamloos-2.png"));
            PDImageXObject logoImg = LosslessFactory.createFromImage(doc, img);
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

    public static PDDocument lostLuggagePDF(Luggage luggage) throws IOException {
        Customer customer = MainApp.getCustomer();
        PDDocument testDocument = new PDDocument();
        BufferedImage img = ImageIO.read(MainApp.class.getResource("/images/Naamloos-2.png"));
        PDImageXObject pdImage = LosslessFactory.createFromImage(testDocument, img);
        PDPage page01 = new PDPage();
        testDocument.addPage(page01);

        PDPageContentStream contents = new PDPageContentStream(testDocument, page01);

        contents.drawImage(pdImage, 50, 718);

        contents.setStrokingColor(Color.black);
        contents.addRect(50, 50, (page01.getMediaBox().getWidth()) - 100, 600);
        contents.stroke();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA_BOLD, 28);
        contents.newLineAtOffset(50, 675);
        String text = "Lost Lugage Registration";
        contents.showText(text);
        contents.endText();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA, 11);
        contents.newLineAtOffset(60, 630);
        text = "Registration number: " + luggage.getRegistrationNr();
        contents.showText(text);
        contents.endText();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA, 11);
        contents.newLineAtOffset(60, 615);
        text = "Airport: " + luggage.getIATA();
        contents.showText(text);
        contents.endText();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA, 11);
        contents.newLineAtOffset(60, 600);
        text = "Luggage Type: " + luggage.getLuggageType();
        contents.showText(text);
        contents.endText();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA, 11);
        contents.newLineAtOffset(60, 585);
        text = "Brand: " + luggage.getBrand();
        contents.showText(text);
        contents.endText();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA, 11);
        contents.newLineAtOffset(60, 570);
        text = "Destination: " + luggage.getDestination();
        contents.showText(text);
        contents.endText();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA, 11);
        contents.newLineAtOffset(60, 555);
        text = "Flight number: " + luggage.getFlightNr();
        contents.showText(text);
        contents.endText();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA, 11);
        contents.newLineAtOffset(60, 540);
        text = "Label number: " + luggage.getLabelNr();
        contents.showText(text);
        contents.endText();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA, 11);
        contents.newLineAtOffset(60, 525);
        text = "Color: " + luggage.getPrimaryColor()
                + (luggage.getSecondaryColor() != null ? " and " + luggage.getSecondaryColor() : "");
        contents.showText(text);
        contents.endText();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA, 11);
        contents.newLineAtOffset(60, 510);
        text = "Your name: " + customer.getFirstName()
                + (customer.getPreposition() != null && !customer.getPreposition().isEmpty()
                ? " " + customer.getPreposition() + " " : " ")
                + customer.getLastName();
        contents.showText(text);
        contents.endText();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA, 11);
        contents.newLineAtOffset(60, 495);
        text = "Extra notes: " + luggage.getNotes();
        text = text.replace("\n", " ").replace("\r", " ");
        contents.showText(text);
        contents.endText();

        contents.close();

        return testDocument;
    }

    public static PDDocument damagedLuggagePDF(String notes) throws IOException {
        Customer customer = MainApp.getCustomer();
        PDDocument testDocument = new PDDocument();
        BufferedImage img = ImageIO.read(MainApp.class.getResource("/images/Naamloos-2.png"));
        PDImageXObject pdImage = LosslessFactory.createFromImage(testDocument, img);
        PDPage page01 = new PDPage();
        testDocument.addPage(page01);

        PDPageContentStream contents = new PDPageContentStream(testDocument, page01);

        contents.drawImage(pdImage, 50, 718);

        contents.setStrokingColor(Color.black);
        contents.addRect(50, 50, (page01.getMediaBox().getWidth()) - 100, 600);
        contents.stroke();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA_BOLD, 28);
        contents.newLineAtOffset(50, 675);
        String text = "Damaged Lugage Registration";
        contents.showText(text);
        contents.endText();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA, 11);
        contents.newLineAtOffset(60, 630);
        text = "Your name: " + customer.getFirstName()
                + (customer.getPreposition() != null && !customer.getPreposition().isEmpty()
                ? " " + customer.getPreposition() + " " : " ")
                + customer.getLastName();
        contents.showText(text);
        contents.endText();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA, 11);
        contents.newLineAtOffset(60, 615);
        text = "Notes: " + notes;
        text = text.replace("\n", " ").replace("\r", " ");
        contents.showText(text);
        contents.endText();

        contents.close();

        return testDocument;
    }
}
