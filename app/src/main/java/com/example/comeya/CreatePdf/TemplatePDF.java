package com.example.comeya.CreatePdf;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.comeya.utils.FacData;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Calendar;

import static com.loopj.android.http.AsyncHttpClient.getUrlWithQueryString;
import static com.loopj.android.http.AsyncHttpClient.log;

public class TemplatePDF {
    private  Context context;
    private  File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle=new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
    private Font fSubTitle=new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD);
    private Font fText=new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
    private Font fHighText=new Font(Font.FontFamily.TIMES_ROMAN,15,Font.BOLD, BaseColor.RED);

    public TemplatePDF(Context context) {
        this.context=context;
    }
    public void OpenDocument(){
        CreateFile();
        try {
            document =new Document(PageSize.A4);
                pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(pdfFile));
            document.open();
        }
        catch (Exception e){
            log.e("openDocument",e.toString());
        }
    }
    private void CreateFile(){
        File folder=new File(Environment.getExternalStorageDirectory().toString(),"PDF");
        if(!folder.exists()){
            folder.mkdir();
        }
        pdfFile=new File(folder, FacData.ID_FAC +"-Ticket.pdf");
        //log.d("esta es la direciooooon uri 2",""+pdfFile);
    }
    public void closeDocument(){
        document.close();
    }
    public void addMetaData(String title, String subject, String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }
    public void addTitles(String title, String subTitle, String date){
        try {
            paragraph =new Paragraph();
            addChild(new Paragraph(title,fTitle));
            addChild(new Paragraph(subTitle,fSubTitle));
            addChild(new Paragraph("Generado:"+date,fHighText));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        } catch (Exception e) {
            log.e("openDocument",e.toString());
        }
    }
    private void addChild(Paragraph childParagraf ){
        childParagraf.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraf);
    }
    public void addParagraph(String text){
        try {
        paragraph=new Paragraph(text,fText);
        paragraph.setSpacingAfter(5);
        paragraph.setSpacingBefore(5);
        document.add(paragraph);
        } catch (Exception e) {
            log.e("addParagraph",e.toString());
        }
    }
    public void CreateTable(String[]header, ArrayList<String[]>clients){
        try {
        paragraph=new Paragraph();
        paragraph.setFont(fText);
        PdfPTable pdfPTable=new PdfPTable(header.length);
        pdfPTable.setWidthPercentage(100);
        PdfPCell pdfPCell;
        int indexC=0;
        while(indexC<header.length){
            pdfPCell=new PdfPCell(new Phrase(header[indexC++],fSubTitle));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBackgroundColor(BaseColor.GREEN);
            pdfPTable.addCell(pdfPCell);
        }
        for(int indexR=0;indexR<clients.size();indexR++){
            String[]row=clients.get(indexR);
            for (indexC=0;indexC<clients.size();indexC++){
                pdfPCell=new PdfPCell(new Phrase(row[indexC]));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setFixedHeight(40);
                pdfPTable.addCell(pdfPCell);
            }
        }
        paragraph.add(pdfPTable);
        document.add(paragraph);
        } catch (Exception e) {
            log.e("createTable",e.toString());
        }
    }
    public void viewAppPDF(Activity activity){
        if(pdfFile.exists()){
            Uri uri=Uri.fromFile(pdfFile);
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri,"application/pdf");

            try {
                activity.startActivityForResult(intent.createChooser(intent, "Selecione la aplicacion"), 10);
                //activity.startActivity(intent);
            }catch (ActivityNotFoundException e){
                activity.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.adobe.reader")));
                Toast.makeText(activity.getApplicationContext(),"no cuentas con una aplicacion para visualizar pdf",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(activity.getApplicationContext(),"no se encontro el archivo",Toast.LENGTH_LONG).show();
        }
    }
    public void copiarIMG(Uri path){
        log.d("este es la direccion..",""+path);
        Image image= null;
        try {
            image = Image.getInstance(path+"");
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        float scaler =((document.getPageSize().getWidth() - document.leftMargin()-document.rightMargin()-0)/image.getWidth())*100;
        image.scalePercent(scaler);
        image.setAlignment(Image.ALIGN_CENTER | image.ALIGN_TOP);
        try {
            document.add(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
