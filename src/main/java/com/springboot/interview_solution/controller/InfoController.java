package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.*;
import com.springboot.interview_solution.dto.LetterDto;
import com.springboot.interview_solution.payload.FileUploadResponse;
import com.springboot.interview_solution.service.FileUploadDownloadService;
import com.springboot.interview_solution.service.InfoService;
import com.springboot.interview_solution.service.LetterService;
import com.springboot.interview_solution.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class InfoController {

    @Autowired
    private final InfoService infoService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final FileUploadDownloadService fileService;
    @Autowired
    private final LetterService letterService;

    private static ArrayList<Grade> gradeList = new ArrayList<Grade>();
    private static final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @RequestMapping(value = "/infoStudent", method = RequestMethod.GET)
    public ModelAndView getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ModelAndView mv = new ModelAndView("upload");
        GradeList gradeInfo = new GradeList();
        gradeList = infoService.getStudentGrade(user);
        gradeInfo.setGrades(gradeList);
        mv.addObject("gradeInfo", gradeInfo);
        Letter letter = letterService.getStudentLetter(user);
        if(letter.getQuestion3() != null && letter.getQuestion3().equals(""))
            letter.setQuestion3(null);
        mv.addObject("letter", letter);
        return mv;
    }

    @RequestMapping(value = "/infoStudent/grade", method = RequestMethod.POST)
    public String postInfo(@ModelAttribute("gradeForm") ArrayList<Grade> gradeInfo){
        System.out.println(gradeInfo);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        infoService.setStudentGrade(gradeInfo, user);
        return "redirect:/infoStudent";
    }

    @RequestMapping(value = "/infoStudent/letter", method = RequestMethod.POST)
    public String postInfo(LetterDto letter){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        letterService.setStudentLetter(letter, user);
        return "redirect:/infoStudent";
    }

//    @RequestMapping(value="/infoStudent/transcript", method=RequestMethod.POST)
//    public FileUploadResponse uploadFile(@RequestParam("transcript") MultipartFile file){
//        String fileName = fileService.storeFile(file);
//
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();
//
//        return new FileUploadResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
//    }

//    @PostMapping(value = "/uploadMultipleFiles/transcripts")
//    public List<FileUploadResponse> uploadMultipleFiles(@RequestParam("transcripts") MultipartFile[] files) {
//        return Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file))
//                .collect(Collectors.toList());
//    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @RequestMapping(value="/infoStudent/transcript", method=RequestMethod.POST)
    public String OCR(@RequestParam("image_uploads") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String fileName = fileService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        HttpSession session = request.getSession();
        session.setAttribute("ocr", fileDownloadUri);
        System.out.println(session.getAttribute("ocr"));

        String url = "http://127.0.0.1:5000/tospring";
        String data = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            String line = null;

            while ((line = br.readLine()) != null) {
                data = data + line + "\n";
            }

            br.close();

            new OCR(data.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "redirect:/infoStudent";
    }
}
