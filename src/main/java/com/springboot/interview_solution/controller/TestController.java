package com.springboot.interview_solution.controller;

import lombok.AllArgsConstructor;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
public class TestController {

    private static PythonInterpreter interpreter;

    @RequestMapping(value = "test-python")
    public ModelAndView getTest() {
        System.setProperty("python.import.site", "false");
        interpreter = new PythonInterpreter();
        interpreter.execfile("src/main/java/com/springboot/interview_solution/python/test.py");

        PyFunction pyFunction = (PyFunction) interpreter.get("test", PyFunction.class);
        PyObject pyObject = pyFunction.__call__();
        ModelAndView mv = new ModelAndView("test");
        System.out.println(pyObject);
//        mv.addObject("test", pyObject.toString());
        return mv;
    }
}
