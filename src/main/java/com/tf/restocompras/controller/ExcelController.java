package com.tf.restocompras.controller;

import com.tf.restocompras.service.ExcelLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/excel")
@Tag(name = "Excel", description = "Endpoints to process Excel files")
public class ExcelController {

    private final ExcelLogService excelLogService;

    public ExcelController(ExcelLogService excelLogService) {
        this.excelLogService = excelLogService;
    }

    @Operation(summary = "Log Excel contents", description = "Uploads an Excel file (.xls/.xlsx) and logs each row to the application logs")
    @PostMapping(value = "/log", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> logExcel(@RequestParam("file") MultipartFile file) {
        int totalRows = excelLogService.processAndLog(file);
        Map<String, Object> body = new HashMap<>();
        body.put("filename", file.getOriginalFilename());
        body.put("size", file.getSize());
        body.put("totalRows", totalRows);
        body.put("message", "Excel processed. Check application logs for row output.");
        return ResponseEntity.ok(body);
    }
}

