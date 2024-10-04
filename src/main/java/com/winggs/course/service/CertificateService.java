package com.winggs.course.service;

import java.io.File;
import java.util.Map;

public interface CertificateService {
    File generatePdfFile(Map<String, String> data, String pdfFileName);
}
