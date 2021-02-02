package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.SchoolInfo;
import com.springboot.interview_solution.repository.SchoolInfoRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.Iterator;

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
        //ArrayList<String> schoolName = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect(url).get();
            Iterator<Element> region = doc.select("region").iterator();
            Iterator<Element> highSchool = doc.select("content").select("schoolname").iterator();
            while(highSchool.hasNext()){
                String schoolInfo = "("+region.next().text()+") "+highSchool.next().text();
                schoolInfoRepository.save(SchoolInfo.builder().name(schoolInfo).build());
            }

        }catch(Exception e){
            System.out.println(e);
        }

    }
}
