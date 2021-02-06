package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.User;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@AllArgsConstructor
@Controller
@SessionAttributes("user")
public class FlaskToSpringTest{

    @Test
    @RequestMapping(value = "/test.do", method = RequestMethod.GET)
    public String ModelAndViewTest(Model model) {
        ModelAndView mv = new ModelAndView("flaskToSpring");

        String url = "http://127.0.0.1:5000/tospring";
        String sb = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            String line = null;

            while ((line = br.readLine()) != null) {
                sb = sb + line + "\n";
            }
            if (sb.toString().contains("200")) {
                System.out.println("test");
//                System.out.println(sb.toString());
            }
            br.close();
            System.out.println(sb);
//            System.out.println(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mv.addObject("test", sb.toString());
        mv.setViewName("flaskToSpring");
        return "flaskToSpring";
    }
}