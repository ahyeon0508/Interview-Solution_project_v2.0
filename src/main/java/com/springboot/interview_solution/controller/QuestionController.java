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
import java.util.*;

@AllArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    /* 질문 리스트 가져오기 */
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

    /* 학과별 질문리스트 가져오기 (학생이 직접 입력한 질문, 교사가 보낸 질문 선택 가능) */
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

    // 질문 검색
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

    /* 내 질문 리스트 가져오기 */
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

    /* 파트별 내 질문 가져오기 */
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

    /* 내가 작성한 질문 중 검색 */
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

    /* 질문 리스트에 추가 질문 저장 */
    @RequestMapping(value = "/myQuestionList/newQuestion")
    public String addNewQuestionBySelf(String question_str){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        questionService.sendQuestionByMe(question_str,user);

        return "redirect:/myQuestionList";
    }

    /* 교사가 특정 학생에게 질문 보낸 것 가져오기 */
    @GetMapping(value = "questionSend/{studentID}")
    public ModelAndView getQuestionSend(@PathVariable String studentID) {
        ModelAndView mv = new ModelAndView("questionSend");
        mv.addObject("studentID", studentID);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User teacher = (User) authentication.getPrincipal();

        List<StudentQuestion> studentQuestions = questionService.getAllStudentQuestionByTeacher(studentID,teacher);
//        System.out.println(studentQuestions.get(0).getQuestion());
        mv.addObject("questions", studentQuestions);
        return mv;
    }문

    /* 교사가 특정 학생에게 질문 보내기 */
    @PostMapping(value = "questionSend/{studentID}")
    public String PostQuestionSend(@PathVariable String studentID, @RequestParam("question") String question_str) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User teacher = (User) authentication.getPrincipal();

        questionService.sendQuestionByTeacher(question_str, studentID, teacher);
        return "redirect:/questionSend/" + studentID;
    }

    /* 교사가 특정 학생에게 보낸 질문 삭제 */
    @GetMapping(value = "questionSend/delete/{questionID}")
    public String DeleteQuestionSend(@PathVariable("questionID") Integer questionID) {
        StudentQuestion question = questionService.searchMyQuestion(questionID);
        questionService.deleteMyQuestion(questionID);
        return "redirect:/questionSend/" + question.getUser().getUserID();
    }

    /* 질문 등록(서비스에서 제공하는 질) */
    @GetMapping(value = "enrollQuestion/")
    public String EnrollQuestionPage(){
        //dept=0
        List<String> commonQuestion = Arrays.asList("자신의 장점과 단점은 무엇인가요?",
            "이 대학(학과)에 지원하게 된 동기는 무엇인가요?",
            "어떤 계기로 봉사활동을 하게 됐나요?",
            "어떤 분이 가장 기억에 남나요?",
            "본인한테 봉사는 어떤 의미인가요?");
        //dept=1
        List<String> computerQuestion = Arrays.asList("벡터와 스칼라의 차이는 무엇인가요? 그리고 벡터의 구성요소에 관해 설명하세요.",
            "프로그램을 제작한다고 가정했을 때, 만들어보고 싶은 프로그램은 무엇인가요?",
            "모바일 시대가 열린 지금, PC와 모바일기기의 공통, 차이점은 무엇인가요?",
            "가장 자신있는 컴퓨터 언어가 무엇인가요?",
            "로봇에 관심이 많다고 적었는데, 현재 그 산업의 동향을 알고 있나요?");
        //dept=2
        List<String> businessQuestion = Arrays.asList("수상경력이 굉장히 많은데 가장 좋아하는 과목을 말하고 이를 경영과 관련시켜서 말해보세요.",
            "생기부에 사회적 이슈들에 굉장히 관심있다고 적혀있는데 최저임금에 대한 기업의 입장의 장단점에 대해 말해보세요.",
            "진로는 홍보전문가이고, 취미는 맛집탐방인데 요새 맛집 마케팅 중에 인상깊은 것 말해볼까요?",
            "독서활동 중에 경영과 관련 책에 대해 이야기해보세요.",
            "회계랑 경영학이 자신한테 맞는다고 생각하시는 이유가 있나요?");
        //dept=3
        List<String> koreanQuestion = Arrays.asList("본인이 생각하는 정보사회의 장단점에 대해 말씀해주세요",
            "국어국문학과를 입학하게 되면 가장 배우고 싶은 것은 무엇인가요?",
            "독서활동이 많은데, 지금 읽은 것들 중에서 친구들에게 어떤 책을 추천하고 싶은가요?",
            "가장 인상깊게 읽은 책이 무엇인가요?",
            "전공을 정할 때 가장 큰 영향을 준 책 한권에 대해 이야기 해주세요.");
        //dept=4
        List<String> biologyQuestion = Arrays.asList("화생공 대신 왜 생명공학부에 지원했는가요?",
            "수학과 생명과학이 관련된 점은 무엇인가요?",
            "단백질의 1,2,3,4차 구조에 대해 설명해주세요.",
            "교감신경과 부교감 신경에 대해 아나요?",
            "본인이 배운 생명이라는 과목 내에서 가장 관심이 많은 내용은 무엇인가요?",
            "바이러스는 생물일까 무생물일까 자신의 생각을 말해보세요.");

        //enroll Questions
        for(Iterator<String> itr=commonQuestion.iterator();itr.hasNext();){
            questionService.enrollQuestion(itr.next(),0);
        }
        for(Iterator<String> itr=computerQuestion.iterator();itr.hasNext();){
            questionService.enrollQuestion(itr.next(),1);
        }
        for(Iterator<String> itr=businessQuestion.iterator();itr.hasNext();){
            questionService.enrollQuestion(itr.next(),2);
        }
        for(Iterator<String> itr=koreanQuestion.iterator();itr.hasNext();){
            questionService.enrollQuestion(itr.next(),3);
        }
        for(Iterator<String> itr=biologyQuestion.iterator();itr.hasNext();){
            questionService.enrollQuestion(itr.next(),4);
        }
        return "success";
    }
}
