package com.example.demo.service;

import com.example.demo.dto.ApplicantDto;
import com.example.demo.dto.RegisterDto;
import com.example.demo.model.Applicant;
import com.example.demo.model.CandidateLocation;
import com.example.demo.model.IndexUnit;
import com.example.demo.repository.ApplicantRepository;
import com.example.demo.repository.IndexUnitRepository;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
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

    private static int applicantNum = 1;

   public  void save(IndexUnit applicant){ indexUnitRepository.save(applicant);}

    public Applicant findByIdInt(int id) {
        return (Applicant) applicationRepository.findById(id).orElse(null);
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
            ret.add(dto);
        }
        return ret;
    }

    public IndexUnit register(RegisterDto dto) throws Exception {
        MultipartFile cv = dto.getCv();
        MultipartFile coverLetter = dto.getCoverLetter();
        String cvContent = saveAndReadPdf(cv);
        String clContent = saveAndReadPdf(coverLetter);

        Applicant a = new Applicant();
        a.setName(dto.getName());
        a.setSurname(dto.getSurname());
        a.setCvName(cv.getOriginalFilename());
        a.setClName(coverLetter.getOriginalFilename());
        a.setEducation(dto.getEducation());
        a.setStreet(dto.getStreet());
        a.setCity(dto.getCity());
        applicationRepository.save(a);

        IndexUnit iu = new IndexUnit();
        //iu.setId(String.valueOf(ApplicantService.applicantNum));
        iu.setId(a.getId().toString());
        iu.setName(dto.getName());
        iu.setSurname(dto.getSurname());
        iu.setEducation(dto.getEducation());
        iu.setClContent(clContent);
        iu.setCvContent(cvContent);

        CandidateLocation l = locationService.getLocationFromAddress(dto.getCity());
       GeoPoint g = new GeoPoint(l.getLat(),l.getLon());
        iu.setLocation(g);

        ++ApplicantService.applicantNum;
        save(iu);

      //  System.out.println(l.getLat() + "-----" + l.getLon());
        return iu;
    }

    private String saveAndReadPdf(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        File resourcesDir = new File("src/main/resources");
        if (!resourcesDir.exists()) {
            resourcesDir.mkdirs();
        }

        File pdfFile = new File(resourcesDir, fileName);
        FileOutputStream outputStream = new FileOutputStream(pdfFile);

        FileCopyUtils.copy(inputStream, outputStream);

        inputStream.close();
        outputStream.close();


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
}
