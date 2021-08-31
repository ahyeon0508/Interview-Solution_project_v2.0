package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.Question;
import com.springboot.interview_solution.domain.RecordData;
import com.springboot.interview_solution.domain.StudentQuestion;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.service.InterviewService;
import com.springboot.interview_solution.service.QuestionService;
import com.springboot.interview_solution.service.ReportService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/student/interview")
public class InterviewController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private InterviewService interviewService;

    ArrayList<Question> interviewQuestions;
    RecordData recordData;
    Long executionTime;

    /*Show Questions and select it*/
    //show my Questions
    @RequestMapping(value = "")
    public ModelAndView interStart(){
        // get user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if(!authentication.getPrincipal().equals("anonymousUser")){
            user = (User) authentication.getPrincipal();
            System.out.println(user);
        }else{
            user = null;
        }

        //get Users' Questions
        List<StudentQuestion> myQuestions = questionService.getAllStudentQuestion(user);

        ModelAndView mv = new ModelAndView();
        mv.addObject("myQuestionList", myQuestions);
        mv.setViewName("inter_start");
        return mv;
    }
    //show All Questions
    @RequestMapping(value = "/question")
    public ModelAndView interStart_SelectAllQuestion(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<Question> questions = new ArrayList<Question>();
        List<Question> myQuestions = new ArrayList<Question>();

        questions = questionService.getAllQuestion();
        myQuestions = questionService.getAllMyQuestion(user);
        questions = questionService.subtractQuestion(myQuestions,questions);

        ModelAndView mv = new ModelAndView();
        mv.addObject("questionList", questions);
        mv.setViewName("inter_start");
        return mv;
    }

    //select question in QuestionList
    @RequestMapping(value = "/question/{dept}")
    public ModelAndView interStart_selectQuestion(@PathVariable int dept){
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
            myQuestions = null;
            mv.addObject("myQuestionList",myQuestions);
        }

        mv.addObject("questionList",questions);
        mv.setViewName("inter_start");
        return mv;
    }

    //js: nonstar->star
    @RequestMapping(value = "/check/{questionID}")
    public String checkQuestion(@PathVariable int questionID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        questionService.enrollMyQuestion(questionID,user);
        return "redirect:/student/interview";
    }

    //js: star->nonstar
    @RequestMapping(value = "/uncheck/{questionID}")
    public String uncheckQuestion(@PathVariable int questionID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        //delete the question in studentRepository
        questionService.deleteMyQuestion(questionID);
        return "redirect:/student/interview";
    }

    /*Prepare the Interview and select Questions randomly*/
    @RequestMapping(value = "/setting")
    public ModelAndView interSetting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        //get Users' questions
        List<StudentQuestion> myQuestions = questionService.getAllStudentQuestion(user);
        interviewQuestions = new ArrayList<Question>();

        //pick up three questions from Users' Questions Or Randomly
        if(!myQuestions.isEmpty()){
            //from Users' Questions
            int myquestionSize = myQuestions.size();
            if (myquestionSize < 3){
                for(int i=0;i<myquestionSize;i++){
                    interviewQuestions.add(myQuestions.get(i).getQuestion());
                }
                List<Question> questions = questionService.getAllQuestion();
                List<Question> questionsInMyQ = questionService.getQuestionListInStudentQuestion(myQuestions);
                questions = questionService.subtractQuestion(questionsInMyQ,questions);

                interviewQuestions.addAll(interviewService.selectQuestion(questions,3 - myquestionSize));

            }else{
                interviewQuestions.addAll(interviewService.selectMyQuestion(myQuestions,3));
            }

        }else{
            //Randomly
            List<Question> questions = questionService.getAllQuestion();
            interviewQuestions.addAll(interviewService.selectQuestion(questions,3));
        }

        //create report
        Long reportID = reportService.setReport(user,interviewQuestions);

        ModelAndView mv = new ModelAndView();
        mv.addObject("reportID",reportID);
        mv.setViewName("inter_setting");
        return mv;
    }

    /*Interview Page*/
    @RequestMapping(value = "/question1/{reportID}")
    public ModelAndView interviewQ1(@PathVariable Long reportID){
        ModelAndView mv = new ModelAndView();
        mv.addObject("question",interviewQuestions.get(0).getQuestion());
        mv.addObject("reportID",reportID);
        mv.setViewName("inter_q1");
        return mv;
    }
    @RequestMapping(value = "/question2/{reportID}")
    public ModelAndView interviewQ2(@PathVariable Long reportID){
        ModelAndView mv = new ModelAndView();
        mv.addObject("question",interviewQuestions.get(1).getQuestion());
        mv.addObject("reportID",reportID);
        mv.setViewName("inter_q2");
        return mv;
    }
    @RequestMapping(value = "/question3/{reportID}")
    public ModelAndView interviewQ3(@PathVariable Long reportID){
        ModelAndView mv = new ModelAndView();
        mv.addObject("question",interviewQuestions.get(2).getQuestion());
        mv.addObject("reportID",reportID);
        mv.setViewName("inter_q3");
        return mv;
    }

    /*Start Interview*/
    @ResponseBody
    @RequestMapping(value = "/question/record", method = RequestMethod.POST)
    public Map<String,Object> startVideo(HttpServletResponse response,@RequestParam String name, @RequestParam int question ,@RequestParam int reportID) throws IOException {
        Map<String,Object> data = new HashMap<String, Object>();

        if(name.equals("start")){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();

            data.put("result","success");

            recordData = new RecordData();
            executionTime = System.currentTimeMillis();
            interviewService.recordingVideo(user.getUserID(),Integer.toString(reportID),Integer.toString(question),recordData);
        }else{
            data.put("result","error");
        }

        return data;
    }

    /*Stop Interview*/
    @ResponseBody
    @RequestMapping(value = "/question/stop")
    public Map<String,Object> stopVideo(@RequestParam  HashMap<Object,Object> param){
        Map<String,Object> data = new HashMap<String, Object>();
        try{
            String name = param.get("name").toString();
            int question = Integer.parseInt(param.get("question").toString());
            int reportID = Integer.parseInt(param.get("reportID").toString());

            if(name.equals("finish")){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                User user = (User) authentication.getPrincipal();

                data.put("result","success");

                Long stopTime = System.currentTimeMillis();
                interviewService.stopVideo(user.getUserID(),Integer.toString(reportID),Integer.toString(question),recordData);
                executionTime = (stopTime - executionTime)/1000;
                interviewService.makeFinalVideo(user.getUserID(),Integer.toString(reportID),Integer.toString(question),executionTime);
            }else{
                data.put("result","error");
            }

        }catch (Exception e){
            e.printStackTrace();
            data.put("result","error");
        }

        return data;
    }

}
