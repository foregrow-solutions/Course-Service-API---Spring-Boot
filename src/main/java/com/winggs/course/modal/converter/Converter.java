package com.winggs.course.modal.converter;

import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public interface Converter {
    File convert(Document document, String outputPdfFilePath) throws IOException;
}
