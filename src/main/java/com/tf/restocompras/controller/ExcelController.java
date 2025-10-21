package com.tf.restocompras.controller;

import com.tf.restocompras.model.excel.ItemUpdateResponse;
import com.tf.restocompras.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/excel")
@Tag(name = "Excel", description = "Endpoints to process Excel files")
public class ExcelController {

    private final ExcelService excelService;

    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @Operation(summary = "Update Create Items", description = "Uploads an Excel file (.xls/.xlsx) and update/create items")
    @PostMapping(value = "/items", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ItemUpdateResponse> logExcel(@RequestParam("file") MultipartFile file) {
        ItemUpdateResponse response = excelService.processAndLog(file);
        return ResponseEntity.ok(response);
    }
}
