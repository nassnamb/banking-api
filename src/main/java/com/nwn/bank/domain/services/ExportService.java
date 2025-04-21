package com.nwn.bank.domain.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nwn.bank.domain.entities.Transaction;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExportService {

    public byte[] generateTransactionsPdf(List<Transaction> transactions, String accountNumber) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Titre
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Historique des transactions", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        // Informations compte
        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Paragraph accountInfo = new Paragraph("Compte: " + accountNumber, infoFont);
        accountInfo.setSpacingAfter(10f);
        document.add(accountInfo);

        // Tableau
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        // En-têtes
        addTableHeader(table);

        // Données
        for (Transaction tx : transactions) {
            addTransactionRow(table, tx);
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }

    public byte[] generateTransactionsCsv(List<Transaction> transactions) throws Exception {
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer);

        // En-tête CSV
        csvWriter.writeNext(new String[]{
                "Date", "Type", "Montant", "Compte source", "Compte destination", "Description"
        });

        // Données
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        for (Transaction tx : transactions) {
            csvWriter.writeNext(new String[]{
                    tx.getDateOperation().format(formatter),
                    tx.getTypeOperation(),
                    tx.getMontant().toString(),
                    tx.getCompteSource() != null ? tx.getCompteSource() : "",
                    tx.getCompteDestination() != null ? tx.getCompteDestination() : "",
                    tx.getDescription() != null ? tx.getDescription() : ""
            });
        }

        csvWriter.close();
        return writer.toString().getBytes("UTF-8");
    }

    private void addTableHeader(PdfPTable table) {
        String[] headers = {"Date", "Type", "Montant", "Compte source", "Compte destination", "Description"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPhrase(new Phrase(header));
            table.addCell(cell);
        }
    }

    private void addTransactionRow(PdfPTable table, Transaction tx) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        table.addCell(tx.getDateOperation().format(formatter));
        table.addCell(tx.getTypeOperation());
        table.addCell(tx.getMontant().toString());
        table.addCell(tx.getCompteSource() != null ? tx.getCompteSource() : "-");
        table.addCell(tx.getCompteDestination() != null ? tx.getCompteDestination() : "-");
        table.addCell(tx.getDescription() != null ? tx.getDescription() : "-");
    }
}
