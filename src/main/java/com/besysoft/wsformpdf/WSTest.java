package com.besysoft.wsformpdf;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lzielinski on 10/03/2016.
 */
@WebService(name = "WSTEST")
public class WSTest {
  @WebMethod
  public String sayHelloWorldFrom(String from) {
    String result = "Hello, world, from " + from;
    System.out.println(result);
    return result;
  }


  @WebMethod(operationName = "TEST2")
  public InputStream getPdf2(@WebParam(name = "NOMBRE")String name)  {
    PDDocument document = new PDDocument();
    PDPage page = new PDPage();

    document.addPage(page);
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    // Create a new font object selecting one of the PDF base fonts
    PDFont font = PDType1Font.HELVETICA_BOLD;


    try {
      // Start a new content stream which will "hold" the to be created content
      PDPageContentStream contentStream = new PDPageContentStream(document, page);

      // Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
      contentStream.beginText();
      contentStream.setFont(font, 12);
      contentStream.moveTextPositionByAmount(100, 700);
      contentStream.drawString("Hello World "+name);
      contentStream.endText();

      // Make sure that the content stream is closed:
      contentStream.close();
      // Save the results and e  nsure that the document is properly closed:
      document.save(out);
      document.close();
    }catch (IOException e){
      e.printStackTrace();
    } catch (COSVisitorException e) {
      e.printStackTrace();
    }
    PDStream ps=new PDStream(document);
    try {
      return ps.createInputStream();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

}
