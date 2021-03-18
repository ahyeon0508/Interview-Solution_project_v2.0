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
        List<Question> result = new ArrayList<Question>();
        for(Question q: questions){
            if(q.getDepartment()==department){
                result.add(q);
            }
        }
        return result;
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
    public List<StudentQuestion> searchMyQuestion(String word,User user){
        List<StudentQuestion> allQuestions = studentQuestionRepository.findAllByUser(user);
        List<StudentQuestion> questions = new ArrayList<StudentQuestion>();

        for(Iterator<StudentQuestion> itr = allQuestions.iterator(); itr.hasNext();){
            StudentQuestion n = itr.next();
            if(n.getQuestion().getQuestion().contains(word)){
                questions.add(n);
            }
        }
        return questions;
    }

    //search MyQuestionById
    public StudentQuestion searchMyQuestion(Integer questionID){
        Long id = new Long(questionID);
        StudentQuestion question = studentQuestionRepository.findById(id).orElseThrow();
        return question;
    }

    //pick up the Question List In StudentQuestion List
    public List<Question> getQuestionListInStudentQuestion(List<StudentQuestion> studentQuestions){
        List<Question> questions = new ArrayList<Question>();
        for(StudentQuestion q: studentQuestions){
            questions.add(q.getQuestion());
        }
        return questions;
    }

    // subtract myQuestions from Questions
    public List<Question> subtractQuestion(List<Question> myQuestionList, List<Question> questionList){
        List<Question> questions = questionList;
        for(Question myQ: myQuestionList){
            questions.remove(myQ);
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
    public void sendQuestionByTeacher(String question_str,String studentID, User teacher){
        //part=2
        Question question = questionRepository.findByQuestion(question_str);
        User student = userRepository.findByUserID(studentID).orElseThrow();
        studentQuestionRepository.save(StudentQuestion.builder().question(question).student(student).teacher(teacher).part(2).build());
    }

    //get All StudentQuestion by User
    public List<StudentQuestion> getAllStudentQuestion(User user){
        List<StudentQuestion> studentQuestions = studentQuestionRepository.findAllByUser(user);
        return studentQuestions;
    }

    //get All StudentQuestion by User and Part
    public List<StudentQuestion> getAllStudentQuestionByPart(User user,Integer part){
        List<StudentQuestion> studentQuestions = studentQuestionRepository.findAllByUserAndPart(user,part);
        return studentQuestions;
    }

    public List<StudentQuestion> getAllStudentQuestionByTeacher(String studentID,User teacher){
        User student = userRepository.findByUserID(studentID).orElseThrow();
        List<StudentQuestion> studentQuestions = studentQuestionRepository.findAllByUserAndTeacher(student,teacher);
        return studentQuestions;
    }

    //delete StudentQuestion By ID
    public void deleteMyQuestion(Integer id){
        long studentQuestionID = new Long(id);
        studentQuestionRepository.deleteById(studentQuestionID);
    }

    //delete StudentQuestion By QuestionID And User
    public void deleteMyQuestionByQuestionID(User user,Integer questionID){
        long id = new Long(questionID);
        Question question = questionRepository.findById(id).orElseThrow();
        System.out.println("questionID:"+questionID);
        StudentQuestion findQuestion = studentQuestionRepository.findByUserAndQuestion(user,question);
        long myQuestion = findQuestion.getId();
        studentQuestionRepository.deleteById(myQuestion);
    }




}
