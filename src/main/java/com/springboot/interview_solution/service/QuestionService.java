package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Question;
import com.springboot.interview_solution.domain.StudentQuestion;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.repository.QuestionRepository;
import com.springboot.interview_solution.repository.StudentQuestionRepository;
import com.springboot.interview_solution.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final StudentQuestionRepository studentQuestionRepository;
    private final UserRepository userRepository;


    public QuestionService(QuestionRepository questionRepository, StudentQuestionRepository studentQuestionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.studentQuestionRepository = studentQuestionRepository;
        this.userRepository = userRepository;
    }

    public Boolean enrollQuestion(String question,Integer department){
        if(questionRepository.existsByQuestion(question)){
            //question exists
            return false;
        }else{
            //post the question
            questionRepository.save(Question.builder().question(question).department(department).build());
            return true;
        }
    }

    //get All Questions
    public List<Question> getAllQuestion(){
        List<Question> questions = questionRepository.findAll();
        return questions;
    }

    //get Questions By department
    public List<Question> getQuestionByDept(Integer department){
        List<Question> questions = questionRepository.findAllByDepartment(department);
        return questions;
    }

    //get All myQuestions
    public List<Question> getAllMyQuestion(User user){
        List<Question> questions = studentQuestionRepository.findAllQuestionByUser(user);
        return questions;
    }

    //get myQuestions By department
    public List<Question> getMyQuestionByDept(Integer department, User user){
        List<Question> questions = studentQuestionRepository.findAllQuestionByUser(user);
        for(Question q: questions){
            if(q.getDepartment().equals(department)==false){
                questions.remove(q);
            }
        }
        return questions;
    }
    public Question getQuestionByID(Integer questionID){
        Long id = new Long(questionID);
        Question question = questionRepository.findById(id).orElseThrow();
        return question;
    }
    public Question getMyQuestionByID(Integer questionID, User user){
        Long id = new Long(questionID);
        StudentQuestion myQuestion = studentQuestionRepository.findById(id).orElseThrow();
        Question question = myQuestion.getQuestion();
        return question;
    }

    //search Question
    public List<Question> searchQuestion(String word){
        List<Question> allQuestions = questionRepository.findAll();
        List<Question> questions = new ArrayList<Question>();

        for(Iterator<Question> itr = allQuestions.iterator(); itr.hasNext();){
            Question n = itr.next();
            if(n.getQuestion().contains(word)){
                questions.add(n);
            }
        }
        return questions;
    }

    //search MyQuestion
    public List<Question> searchMyQuestion(String word,User user){
        List<Question> allQuestions = studentQuestionRepository.findAllQuestionByUser(user);
        List<Question> questions = new ArrayList<Question>();

        for(Iterator<Question> itr = allQuestions.iterator(); itr.hasNext();){
            Question n = itr.next();
            if(n.getQuestion().contains(word)){
                questions.add(n);
            }
        }
        return questions;
    }

    // subtract myQuestions from Questions
    public List<Question> subtractQuestion(List<Question> myQuestionList, List<Question> questionList){
        List<Question> questions = myQuestionList;
        for(Question myQ: questions){
            for(Question q: questionList){
                if(q.getId().equals(myQ.getId())){
                    questions.remove(q);
                }
            }
        }
        return questions;
    }

    // question -> Student Question
    public void enrollMyQuestion(Integer QuestionID,User student){
        long id = new Long(QuestionID);
        Question question = questionRepository.findById(id).orElseThrow();
        studentQuestionRepository.save(StudentQuestion.builder().question(question).student(student).part(0).build());
    }
    //enroll new Student Question by Me(Student)
    public void sendQuestionByMe(String question_str,User student){
        //part=1
        Question question = questionRepository.findByQuestion(question_str);
        studentQuestionRepository.save(StudentQuestion.builder().question(question).student(student).part(1).build());
    }

    //enroll new Student Question by Teacher
    public void sendQuestionByTeacher(String question_str,String studentName){
        //part=2
        Question question = questionRepository.findByQuestion(question_str);
        User student = userRepository.findByUsername(studentName).orElseThrow();
        studentQuestionRepository.save(StudentQuestion.builder().question(question).student(student).part(2).build());
    }

    public void deleteMyQuestion(User user,Integer questionID){
        long id = new Long(questionID);
        Question question = questionRepository.findById(id).orElseThrow();
        Question findQuestion = studentQuestionRepository.findQuestionByUserAAndQuestion(user,question);
        long myQuestion = studentQuestionRepository.findQuestionByUserAAndQuestion(user,question).getId();
        studentQuestionRepository.deleteById(myQuestion);
    }



}
