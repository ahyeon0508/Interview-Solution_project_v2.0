package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.Question;
import com.springboot.interview_solution.domain.StudentQuestion;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.script.ScriptContext;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    @RequestMapping(value = "/questionList")
    public ModelAndView questionList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Question> questions = questionService.getAllQuestion();
        ModelAndView mv = new ModelAndView();

        if(!authentication.getPrincipal().equals("anonymousUser")){
            User user = (User) authentication.getPrincipal();
            List<Question> myQuestions = questionService.getAllMyQuestion(user);
            questions = questionService.subtractQuestion(myQuestions,questions);
            mv.addObject("myQuestionList",myQuestions);
            mv.addObject("User",user);
        }else{
            List<Question> myQuestions = null;
            User user = null;
            mv.addObject("myQuestionList",myQuestions);
            mv.addObject("User",user);
        }

        mv.addObject("questionList",questions);
        mv.setViewName("questionlist");

        return mv;
    }

    @RequestMapping(value = "/questionList/{dept}")
    public ModelAndView questionListByDept(@PathVariable int dept){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView mv = new ModelAndView();
        List<Question> questions = new ArrayList<Question>();

        if(dept==999){
            for(int i=1;i<5;i++){
                questions.addAll(questionService.getQuestionByDept(i));
            }
        }else{
            questions = questionService.getQuestionByDept(dept);
        }

        if(!authentication.getPrincipal().equals("anonymousUser")){
            User user = (User) authentication.getPrincipal();
            List<Question> myQuestions = new ArrayList<Question>();
            if(dept==999){
                for(int i=1;i<5;i++){
                    myQuestions.addAll(questionService.getMyQuestionByDept(i,user));
                }
            }else{
                myQuestions = questionService.getMyQuestionByDept(dept,user);
            }
            questions = questionService.subtractQuestion(myQuestions,questions);
            mv.addObject("myQuestionList",myQuestions);
            mv.addObject("User",user);
        }

        mv.addObject("questionList",questions);
        mv.setViewName("questionlist");
        return mv;
    }
    //ajax - nonstar->star
    @RequestMapping(value = "/questionList/check/{questionID}")
    public String checkQuestion(@PathVariable int questionID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        questionService.enrollMyQuestion(questionID,user);
        return "redirect:/questionList";
    }

    //ajax: star->nonstar
    @RequestMapping(value = "/questionList/uncheck/{questionID}")
    public String uncheckQuestion(@PathVariable int questionID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        //delete the question in studentRepository
        questionService.deleteMyQuestionByQuestionID(user,questionID);
        return "redirect:/questionList";
    }

    //search
    @RequestMapping(value = "/questionList",method = RequestMethod.POST)
    public ModelAndView searchQuestion(@RequestParam("question_search")String word){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Question> questions = questionService.searchQuestion(word);
        ModelAndView mv = new ModelAndView();

        if(!authentication.getPrincipal().equals("anonymousUser")){
            User user = (User) authentication.getPrincipal();
            List<Question> myQuestions = questionService.getQuestionListInStudentQuestion(questionService.searchMyQuestion(word,user));
            questions = questionService.subtractQuestion(myQuestions,questions);
            mv.addObject("myQuestionList",myQuestions);
        }

        mv.addObject("questionList",questions);
        mv.setViewName("questionlist");

        return mv;
    }

    @RequestMapping(value = "/myQuestionList")
    public ModelAndView myQuestionList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if(!authentication.getPrincipal().equals("anonymousUser")){
            user = (User) authentication.getPrincipal();
            System.out.println(user);
        }else{
            user = null;
        }

        List<StudentQuestion> myQuestions = questionService.getAllStudentQuestion(user);

        ModelAndView mv = new ModelAndView();
        mv.addObject("myQuestionList", myQuestions);
        mv.addObject("User", user);
        mv.setViewName("myquestion");
        return mv;
    }

    @RequestMapping(value = "/myQuestionList/{part}")
    public ModelAndView myQuestionListByPart(@PathVariable int part){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<StudentQuestion> myQuestions = questionService.getAllStudentQuestionByPart(user,part);

        ModelAndView mv = new ModelAndView();
        mv.addObject("myQuestionList",myQuestions);
        mv.setViewName("myquestion");

        return mv;
    }

    // star->nonstar in My QuestionList
    @RequestMapping(value = "/myQuestionList/uncheck/{questionID}")
    public String uncheckMyQuestion(@PathVariable int questionID) {
        //delete the question in studentRepository
        questionService.deleteMyQuestion(questionID);
        return "redirect:/myQuestionList";
    }

    //search
    @RequestMapping(value = "/myQuestionList",method = RequestMethod.POST)
    public ModelAndView searchMyQuestion(@RequestParam("question_search")String word){
        ModelAndView mv = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<StudentQuestion> myQuestions = questionService.searchMyQuestion(word,user);
        mv.addObject("myQuestionList",myQuestions);
        mv.setViewName("myquestion");

        return mv;
    }

    //add new Question in MyQuestionList
    @RequestMapping(value = "/myQuestionList/newQuestion")
    public String addNewQuestionBySelf(String question_str){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        questionService.sendQuestionByMe(question_str,user);

        return "redirect:/myQuestionList";
    }

    //    studentId : report.student.userID
    @GetMapping(value = "questionSend/{studentID}")
    public ModelAndView getQuestionSend(@PathVariable String studentID) {
        ModelAndView mv = new ModelAndView("questionSend");
        mv.addObject("studentID", studentID);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User teacher = (User) authentication.getPrincipal();

        List<StudentQuestion> studentQuestions = questionService.getAllStudentQuestionByTeacher(studentID,teacher);
        System.out.println(studentQuestions.get(0).getQuestion());
        mv.addObject("questions", studentQuestions);
        return mv;
    }

    @PostMapping(value = "questionSend/{studentID}")
    public String PostQuestionSend(@PathVariable String studentID, @RequestParam("question") String question_str) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User teacher = (User) authentication.getPrincipal();

        questionService.sendQuestionByTeacher(question_str, studentID, teacher);
        return "redirect:/questionSend/" + studentID;
    }

    @GetMapping(value = "questionSend/delete/{questionID}")
    public String DeleteQuestionSend(@PathVariable("questionID") Integer questionID) {
        StudentQuestion question = questionService.searchMyQuestion(questionID);
        questionService.deleteMyQuestion(questionID);
        return "redirect:/questionSend/" + question.getUser().getUserID();
    }
}
