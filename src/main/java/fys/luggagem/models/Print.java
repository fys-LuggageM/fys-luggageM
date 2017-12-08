/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys.luggagem.models;

import fys.luggagem.PDFExport;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 *
 * @author jordan
 */
public class Print {

    public static void printPdf() {
        try {
            PDDocument document = PDFExport.pdf();
            PrinterJob pjob = PrinterJob.getPrinterJob();
            pjob.setPageable(new PDFPageable(document));

            if (pjob.printDialog()) {
                pjob.print();
            }
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
}
