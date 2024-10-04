package com.winggs.course.service.impl;

import com.winggs.course.modal.Template;
import com.winggs.course.modal.common.ApplicationException;
import com.winggs.course.modal.common.ErrorCode;
import com.winggs.course.service.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateServiceImpl implements CertificateService {

    @Value("${common.converter.destination-dir}")
    private String pdfGeneratorOutputDir;

    @Override
    public File generatePdfFile(Map<String, String> params, String pdfFileName) {
        try {
            Template template = new Template("ct001");
            return template.generatePdf(params, pdfGeneratorOutputDir + params.getOrDefault("output_filename", pdfFileName) + ".png");
        } catch (IOException iox) {
            throw new ApplicationException(ErrorCode.BAD_REQUEST, iox.getMessage(), iox.getCause());
        }
    }
}
