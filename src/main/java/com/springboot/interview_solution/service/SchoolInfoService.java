package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.SchoolInfo;
import com.springboot.interview_solution.repository.SchoolInfoRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SchoolInfoService {

    private final SchoolInfoRepository schoolInfoRepository;

    private String apiKey = "6d14f6536718c067d03750f8226d2e8c";
    private String totalCount = "2395";
    private String url = "http://career.go.kr/cnet/openapi/getOpenApi?apiKey=" + apiKey + "&svcType=api&svcCode=SCHOOL&contentType=xml&gubun=high_list&perPage="+totalCount;

    public SchoolInfoService(SchoolInfoRepository schoolInfoRepository) {
        this.schoolInfoRepository = schoolInfoRepository;
    }

    public void collectInfo() {
        try {
            Document doc = Jsoup.connect(url).get();

            for(Element el : doc.select("content")){
                String schoolInfo = "("+el.select("region").text()+") "+el.select("schoolname").text();
                schoolInfoRepository.save(SchoolInfo.builder().name(schoolInfo).build());
            }
        }catch(Exception e){
            System.out.println(e);
        }

    }
    public List<String> findAllByName(String schoolName){
        List<String> allSchoolNames = schoolInfoRepository.findAllSchoolName();
        List<String> schoolNames = new ArrayList<String>();
        for(Iterator<String> iterator= allSchoolNames.iterator(); iterator.hasNext();){
            String n = iterator.next();
            if(n.contains(schoolName)){
                schoolNames.add(n);
            }
        }
        return schoolNames;
    }

}
