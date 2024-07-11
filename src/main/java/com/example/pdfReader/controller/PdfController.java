
package com.example.pdfReader.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.pdfReader.service.PdfService;


@Controller
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFiles(MultipartHttpServletRequest request, Model model) {
        List<MultipartFile> files = request.getFiles("files");
        List<String> pdfTexts = files.stream().map(file -> {
            try {
                return pdfService.extractText(file);
            } catch (IOException e) {
                e.printStackTrace();
                return "Error reading file: " + file.getOriginalFilename();
            }
        }).collect(Collectors.toList());
        
        model.addAttribute("pdfTexts", pdfTexts);
        return "upload";
    }

}