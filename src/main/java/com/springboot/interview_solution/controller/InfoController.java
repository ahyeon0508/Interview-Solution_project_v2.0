package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.*;
import com.springboot.interview_solution.dto.GradeDto;
import com.springboot.interview_solution.dto.LetterDto;
import com.springboot.interview_solution.dto.TranscriptDto;
import com.springboot.interview_solution.service.InfoService;
import com.springboot.interview_solution.service.LetterService;
import com.springboot.interview_solution.service.TranscriptService;
import com.springboot.interview_solution.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@AllArgsConstructor
@Controller
public class InfoController {

    @Autowired
    private final InfoService infoService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final TranscriptService transcriptService;
    @Autowired
    private final LetterService letterService;

    private static final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @RequestMapping(value = "/infoStudent", method = RequestMethod.GET)
    public ModelAndView getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ModelAndView mv = new ModelAndView("upload");
        Transcript transcript = transcriptService.getStudentTranscript(user);
        mv.addObject("transcript", transcript);
        Letter letter = letterService.getStudentLetter(user);
        if(letter.getQuestion3() != null && letter.getQuestion3().equals(""))
            letter.setQuestion3(null);
        mv.addObject("letter", letter);
        return mv;
    }

    @RequestMapping(value = "/infoStudent/visualize", method = RequestMethod.GET)
    public ModelAndView visualize() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ModelAndView mv = new ModelAndView("visualize");
        List<Grade> gradeList = infoService.getStudentGrade(user);
        mv.addObject("gradeList", gradeList);
        return mv;
    }

    @RequestMapping(value = "/infoStudent/grade", method = RequestMethod.POST)
    public String postInfo(GradeDto gradeInfo){
        System.out.println(gradeInfo.getCourse());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        infoService.setStudentGrade(gradeInfo, user);
        return "redirect:/infoStudent";
    }

    @RequestMapping(value = "/infoStudent/grade/{gradeSemester}", method = RequestMethod.POST)
    public String postInfo(@PathVariable String gradeSemester, GradeDto gradeInfo){
        System.out.println(gradeInfo.getNumberOfStudent());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int grade = Character.getNumericValue(gradeSemester.charAt(0));
        int semester = Integer.parseInt(gradeSemester.substring(1));
        gradeInfo.setGrade(grade);
        gradeInfo.setSemester(semester);
        infoService.setStudentGrade(gradeInfo, user);
        return "redirect:/infoStudent";
    }

    @RequestMapping(value = "/infoStudent/grade/{gradeSemester}")
    public ModelAndView getInfoByGradeAndSemester(@PathVariable String gradeSemester){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ModelAndView mv = new ModelAndView("upload");

        int grade = Character.getNumericValue(gradeSemester.charAt(0));
        int semester = Integer.parseInt(gradeSemester.substring(1));
        mv.addObject("gradeSemester", gradeSemester);
        List<Grade> gradeInfo = infoService.getStudentGradeByGradeAndSemester(grade,semester,user);
        mv.addObject("gradeInfo", gradeInfo);
        Transcript transcript = transcriptService.getStudentTranscript(user);
        mv.addObject("transcript", transcript);
        Letter letter = letterService.getStudentLetter(user);
        if(letter.getQuestion3() != null && letter.getQuestion3().equals(""))
            letter.setQuestion3(null);
        mv.addObject("letter", letter);
        return mv;
    }

    @RequestMapping(value = "/infoStudent/grade/delete/{id}", method = RequestMethod.GET)
    public String deleteInfo(@PathVariable Long id) {
        infoService.deleteStudentGrade(id);
        return "redirect:/infoStudent";
    }

    @RequestMapping(value = "/infoStudent/letter", method = RequestMethod.POST)
    public String postInfo(LetterDto letter){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        letterService.setStudentLetter(letter, user);
        return "redirect:/infoStudent";
    }

    @RequestMapping(value = "infoStudent/transcript", method = RequestMethod.POST)
    public String postInfo(TranscriptDto transcriptDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        transcriptService.setStudentTranscript(transcriptDto, user);
        return "redirect:/infoStudent";
    }

    @RequestMapping(value = "/infoStudent/transcript/{grade}")
    public ModelAndView getInfoByGradeAndSemester(@PathVariable int grade){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ModelAndView mv = new ModelAndView("upload");

        List<Grade> gradeInfo = infoService.getStudentGrade(user);
        mv.addObject("gradeInfo", gradeInfo);
        Transcript transcript = transcriptService.getStudentTranscriptByGrade(grade,user);
        mv.addObject("transcript", transcript);
        Letter letter = letterService.getStudentLetter(user);
        if(letter.getQuestion3() != null && letter.getQuestion3().equals(""))
            letter.setQuestion3(null);
        mv.addObject("letter", letter);
        return mv;

    }
}
