package com.example.demo.service;

import com.example.demo.dto.ApplicantDto;
import com.example.demo.dto.HireDto;
import com.example.demo.dto.RegisterDto;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicantService {
    @Autowired
    IndexUnitRepository indexUnitRepository;
    @Autowired
    public ApplicantRepository applicationRepository;

    @Autowired
    CandidateLocationService locationService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private static final Logger logger = LoggerFactory.getLogger(ApplicantService.class);

   public  void save(IndexUnit applicant){ indexUnitRepository.save(applicant);}

    public Applicant findByIdInt(int id) {
        return applicationRepository.findById(id).orElse(null);
    }
    public IndexUnit findById(String id){
        return indexUnitRepository.findById(id).orElse(null);
    }

    public List<ApplicantDto> getAll(){
        List<ApplicantDto> ret = new ArrayList<>();

        for(Applicant a: applicationRepository.findAll()){
            ApplicantDto dto = new ApplicantDto();
            dto.setName(a.getName() + " " + a.getSurname());
            dto.setEducation(a.getEducation());
            dto.setAddress(a.getStreet() + ", " + a.getCity());
            dto.setId(a.getId().toString());
            if(a.getCompany()!= null) {
                dto.setIsHired(true);
            }else {
                dto.setIsHired(false);
            }
            ret.add(dto);
        }
        return ret;
    }

    public IndexUnit register(RegisterDto dto) throws Exception {
        MultipartFile cv = dto.getCv();
        MultipartFile coverLetter = dto.getCoverLetter();

        Applicant a =  Applicant.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .cvName(cv.getOriginalFilename())
                .clName(coverLetter.getOriginalFilename())
                .education(dto.getEducation())
                .street(dto.getStreet())
                .city(dto.getCity())
                .build();
        applicationRepository.save(a);

        IndexUnit iu =  IndexUnit.builder()
                .id(a.getId().toString())
                .name(dto.getName())
                .surname(dto.getSurname())
                .education(dto.getEducation())
                .clContent(saveAndReadPdf(coverLetter))
                .cvContent(saveAndReadPdf(cv))
                .build();

        Location l = locationService.getLocationFromAddress(dto.getCity());
        GeoPoint g = new GeoPoint(l.getLat(),l.getLon());
        iu.setLocation(g);
        save(iu);

        return iu;
    }

    private String saveAndReadPdf(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        File resourcesDir = new File("src/main/resources/documents");
        if (!resourcesDir.exists())
            resourcesDir.mkdirs();


        File pdfFile = new File(resourcesDir, fileName);
        FileOutputStream outputStream = new FileOutputStream(pdfFile);

        FileCopyUtils.copy(inputStream, outputStream);

        inputStream.close();
        outputStream.close();

        return extractPdfText(pdfFile);
    }

    private String extractPdfText(File pdfFile) throws IOException {
        String parsedText;
        PDFParser parser = new PDFParser(new RandomAccessFile(pdfFile, "r"));
        parser.parse();
        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        parsedText = pdfStripper.getText(pdDoc);
        cosDoc.close();
        return parsedText;
    }

    public Boolean hire(HireDto dto){
        Applicant a = applicationRepository.findById(Integer.parseInt(dto.getApplicantId())).orElseGet(null);

        if(a == null)
            return false;

        Company c = companyRepository.findById(Long.valueOf(dto.getCompanyId())).orElseGet(null);

        if(c == null){
            return false;
        }

        Employee e = employeeRepository.findById(Integer.parseInt(dto.getEmployeeId())).orElseGet(null);

        if(e == null){
            return false;
        }

        a.setCompany(c);
        applicationRepository.save(a);

        logger.info("New employment in the company: {}", c.getName());
        logger.info("New successful employment by employee: {}", e.getName());

        return true;
    }
}
