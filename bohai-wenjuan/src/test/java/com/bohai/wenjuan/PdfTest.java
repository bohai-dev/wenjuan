package com.bohai.wenjuan;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfTest {
    
    public static void main(String[] args) {
        

        Document document = new Document(PageSize.A4);
        
        try {  
            
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED); 
            //BaseFont bf = BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            Font font = new Font(bf,20,Font.NORMAL); 
            
            PdfWriter.getInstance(document, new FileOutputStream("D:/test.pdf"));  
            document.open();
            
            //标题居中加粗
            font.setStyle(Font.BOLD);
            Paragraph title = new Paragraph("客户承诺书",font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            //内容
            font.setStyle(Font.NORMAL);
            font.setSize(14);
            Paragraph context = new Paragraph(30,"本人在此郑重承诺，以上题目均为本人全程独立自主完成。如答卷分数未达到标准或者由他人代答，本人自愿撤销本次开户申请。",font);
            context.setSpacingAfter(30);
            context.setSpacingBefore(30);
            context.setFirstLineIndent(30);
            context.setIndentationLeft(44);
            context.setIndentationRight(44);
            document.add(context);
            
            Image image = Image.getInstance("D:/xxx.jpg");
            
            //签字区域
            Paragraph signPara = new Paragraph(50);
            signPara.setSpacingBefore(50);
            signPara.setIndentationLeft(44);
            signPara.setIndentationRight(44);
            
            PdfPTable table = new PdfPTable(3);
            table.setTotalWidth(new float[]{ 100, 100, 80 });
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            signPara.add(table);
            
            //第一行
            {
                PdfPCell cell = new PdfPCell(new Phrase("客户（签字）：",font));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
                
                PdfPCell imageCell = new PdfPCell(image,true);
                imageCell.setBorder(Rectangle.NO_BORDER);
                table.addCell(imageCell);
            }
            
            {
                PdfPCell cell = new PdfPCell(new Phrase(" ",font));
                cell.setColspan(3);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }
            
            //第二行
            {
                PdfPCell cell = new PdfPCell(new Phrase("知识测试监督人员（签字）：",font));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
                
                PdfPCell imageCell = new PdfPCell(image,true);
                imageCell.setBorder(Rectangle.NO_BORDER);
                table.addCell(imageCell);
            }
            
            {
                PdfPCell cell = new PdfPCell(new Phrase(" ",font));
                cell.setColspan(3);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }
            
            //第三行日期
            {
                PdfPCell cell0 = new PdfPCell(new Phrase("",font));
                cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell0.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell0);
                
                PdfPCell cell = new PdfPCell(new Phrase("年        月        日",font));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setColspan(2);
                table.addCell(cell);
            }
            
            document.add(signPara);
            
            document.close();  
            
           } catch (Exception e) { 
               e.printStackTrace();
               System.out.println("file create exception");  
           }  
        
     }

}
