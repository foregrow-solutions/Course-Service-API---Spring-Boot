package com.winggs.course.modal.converter;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

@Slf4j
public class HtmlToPdfConverter implements Converter {

    @Override
    public File convert(Document document, String outputPdfFilePath) throws IOException {
        return  convertIt(document, outputPdfFilePath);
    }

    private File convertIt(Document document, String outputPdfFilePath) throws IOException {
        File outputFile = Paths.get(System.getProperty("user.home")+outputPdfFilePath).toFile();
        if (!outputFile.exists()) {
            if (outputFile.getParentFile().exists()) {
                outputFile.createNewFile();
            } else {
                outputFile.getParentFile().mkdirs();
                outputFile.createNewFile();
            }
//            outputFile.mkdirs();
        }

        OutputStream os = new FileOutputStream(outputFile);
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withUri(outputPdfFilePath);
        builder.toStream(os);
        builder.withW3cDocument(new W3CDom().fromJsoup(document), "/");
        builder.run();
        return outputFile;
    }
}
