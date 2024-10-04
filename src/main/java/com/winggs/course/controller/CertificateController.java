package com.winggs.course.controller;

import com.winggs.course.modal.common.ApplicationException;
import com.winggs.course.modal.common.ErrorCode;
import com.winggs.course.security.AuthenticatedUser;
import com.winggs.course.security.CurrentUser;
import com.winggs.course.service.CertificateService;
import com.winggs.course.service.StudentService;
import com.winggs.course.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@Tag(name = "Certificate APIs", description = "API for manage Student Certificate Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class CertificateController {
    private final UserService userService;
    private final CertificateService certificateService;
    private final StudentService studentService;

    @PutMapping("/certificate/generate")
    public void convertToPdf(@RequestBody Map<String, String> params,
                             @CurrentUser AuthenticatedUser authenticatedUser,
                             HttpServletResponse response) {
//        Map<String, String> map = new HashMap<>();
//        map.put("student_name", "Echo");
//        map.put("student_score", "99%");
//        map.put("completion_date", "20-8-2023");
        var res = certificateService.generatePdfFile(params, "eltd-certificate");
        try {
            Files.copy(res.toPath(), response.getOutputStream());
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-disposition", "attachment;filename=" + res.getName());
            response.flushBuffer();
        } catch (IOException iox) {
            throw new ApplicationException(ErrorCode.BAD_REQUEST, iox.getMessage(), iox.getCause());
        }
    }

}
