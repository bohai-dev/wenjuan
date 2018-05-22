package com.bohai.wenjuan;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;

public class Pdf2Image {
    
    public final  static String  IMG_TYPE_JPG = "jpg";
    public final  static String  IMG_TYPE_PNG = "png";
    
    
    public static void main( String[] args ) throws IOException{
        Pdf2Image pdf2Image = new Pdf2Image();
        pdf2Image.pdf2img("D:\\test.pdf", "D:",IMG_TYPE_PNG);
    }


    /**
     * PDF转图片
     * @param pdfPath pdf文件的路径
     * @param savePath 图片保存的地址
     * @param imgType 图片保存方式
     */
    public void pdf2img(String pdfPath,String savePath,String imgType){
        String fileName = pdfPath.substring(pdfPath.lastIndexOf("\\")+1, pdfPath.length());
        fileName = fileName.substring(0,fileName.lastIndexOf("."));
        InputStream is = null;
        PDDocument pdDocument = null;
        try {
            is = new BufferedInputStream(new FileInputStream(pdfPath));
            //创建pdf文件解析器
            PDFParser parser = new PDFParser(new RandomAccessBuffer(is));
            parser.parse();
            //获取解析后的pdf文档
            pdDocument = parser.getPDDocument();
            //获取pdf渲染器，主要用来后面获取BufferedImage
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            //获取pdf文件总页数
            int pageCount = pdDocument.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                //构造保存文件名称格式
                String saveFileName = savePath+"\\"+fileName+"-"+i+"."+imgType;
                //获取当前页对象
                PDPage page =  pdDocument.getPage(i);
                //图片转换
                pdfPage2Img(page,saveFileName,imgType,renderer,i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(pdDocument != null){
                try {
                    pdDocument.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将pdf单页转换为图片
     * @param page 当页对象
     * @param saveFileName 保存的图片名称
     * @param imgType 保存的图片类型
     * @param renderer 用于获取BufferedImage
     * @param index 页索引
     * @throws IOException
     */
    public void pdfPage2Img(PDPage page,String saveFileName,String imgType,PDFRenderer renderer,int index) throws IOException{
        //构造图片
        BufferedImage img_temp  = renderer.renderImage(index);
        //设置图片格式
        Iterator<ImageWriter> it = ImageIO.getImageWritersBySuffix(imgType);
        //将文件写出
        ImageWriter writer = (ImageWriter) it.next();
        ImageOutputStream imageout = ImageIO.createImageOutputStream(new FileOutputStream(saveFileName));
        writer.setOutput(imageout);
        writer.write(new IIOImage(img_temp, null, null));
    }
}
