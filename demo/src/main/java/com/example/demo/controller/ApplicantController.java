package com.example.demo.controller;

import com.example.demo.dto.ApplicantDto;
import com.example.demo.dto.DownloadFileDto;
import com.example.demo.dto.RegisterDto;
import com.example.demo.model.Applicant;
import com.example.demo.model.IndexUnit;
import com.example.demo.service.ApplicantService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;


@RestController
@RequestMapping("applicant")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ApplicantController {

    private final ApplicantService applicantService;

    @Autowired
    private HttpServletResponse response;

    @PostMapping
    public void save(@RequestBody IndexUnit applicant){
        applicantService.save(applicant);
    }

    @GetMapping("/getAll")
    public List<ApplicantDto> getAll(){
        return applicantService.getAll();
    }


    @GetMapping("/{id}")
    public IndexUnit findById(@PathVariable String id){
        return applicantService.findById(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // Save the file to disk or process it in memory
        System.out.println(file);
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/register", consumes = { "multipart/form-data" })
    public ResponseEntity<?> register(@ModelAttribute RegisterDto dto) throws Exception {
        System.out.println(dto.getCv());

        IndexUnit a = applicantService.register(dto);
        if(a == null){

            return new ResponseEntity<>("Failed registration!!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Success registration", HttpStatus.OK);
    }

    @PostMapping("/downloadFile")
    @ResponseBody public ResponseEntity<Resource> downloadFile(@RequestBody DownloadFileDto dto, HttpServletResponse response) {
        Applicant a = applicantService.findByIdInt(Integer.parseInt(dto.getId()));
        try {
            String filename;

            if (dto.getIsCV()) {
                filename = a.getCvName();
            } else {
                filename = a.getClName();
            }

            File file = new File("src/main/resources/" + filename);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            System.out.println("The length of the file is : " + file.length());
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-disposition", "attachment; filename=" + file);
            response.setContentLength((int) file.length());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
