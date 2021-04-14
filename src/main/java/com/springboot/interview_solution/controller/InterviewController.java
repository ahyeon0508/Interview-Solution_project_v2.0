package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.Question;
import com.springboot.interview_solution.domain.StudentQuestion;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/student/interview")
public class InterviewController {

    @Autowired
    private QuestionService questionService;

    ArrayList<Question> interviewQuestions;

    /*Show Questions and select it*/
    //show my Questions
    @RequestMapping(value = "/")
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
        questionService.deleteMyQuestionByQuestionID(user,questionID);
        return "redirect:/student/interview";
    }

    /*Prepare the Interview and select Questions randomly*/
    @RequestMapping(value = "/setting")
    public ModelAndView interSetting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;

        //valid user
        if(!authentication.getPrincipal().equals("anonymousUser")){
            user = (User) authentication.getPrincipal();
            System.out.println(user);
        }else{
            user = null;
        }

        //get Users' questions
        List<StudentQuestion> myQuestions = questionService.getAllStudentQuestion(user);

        //pick up three questions from Users' Questions Or Randomly
        if(!myQuestions.isEmpty()){
            //from Users' Questions
            int questionSize = myQuestions.size();
            if (questionSize < 3){
                for(int i=0;i<questionSize;i++){
                    interviewQuestions.add(myQuestions.get(i).getQuestion());
                }

                List<Question> questions = questionService.getAllQuestion();
                int extraQuestionSize = 3 - questionSize;
                Random r = new Random();
                int[] overlapNum = new int[extraQuestionSize];
                for(int i=0;i<extraQuestionSize;i++){
                    overlapNum[i] = r.nextInt(questions.size());
                    for(int j=0;j<i;j++){
                        if(overlapNum[i]==overlapNum[j]){
                            i--;
                            continue;
                        }
                    }
                    interviewQuestions.add(questions.get(overlapNum[i]));
                }
            }else{
                Random r = new Random();
                int[] overlapNum = new int[3];
                for(int i=0;i<3;i++){
                    overlapNum[i] = r.nextInt(myQuestions.size());
                    for(int j=0;j<i;j++){
                        if(overlapNum[i]==overlapNum[j]){
                            i--;
                            continue;
                        }
                    }
                    interviewQuestions.add(myQuestions.get(overlapNum[i]).getQuestion());
                }
            }

        }else{
            //Randomly
            List<Question> questions = questionService.getAllQuestion();
            Random r = new Random();
            int[] overlapNum = new int[3];
            for(int i=0;i<3;i++){
                overlapNum[i] = r.nextInt(questions.size());
                for(int j=0;j<i;j++){
                    if(overlapNum[i]==overlapNum[j]){
                        i--;
                        continue;
                    }
                }
                interviewQuestions.add(questions.get(overlapNum[i]));
            }

        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("inter_setting");
        return mv;
    }

    /*Interview Page*/

    /*Start Interview*/

    /*Stop Interview*/

}
