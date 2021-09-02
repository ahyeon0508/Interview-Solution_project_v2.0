package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Letter;
import com.springboot.interview_solution.domain.Transcript;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.TranscriptDto;
import com.springboot.interview_solution.repository.TranscriptRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TranscriptService {

    @Autowired
    private final TranscriptRepository transcriptRepository;
    private JdbcTemplate jdbcTemplate;

    /* 생활기록부 저장 */
    public void setStudentTranscript(TranscriptDto transcriptDto, User user) {
        if(transcriptRepository.findTranscriptByGradeAndUser(transcriptDto.getGrade(),user).isPresent()) {
            Transcript transcript = transcriptRepository.findTranscriptByUser(user).orElseThrow();
            Long id = transcript.getId();
            jdbcTemplate.update("update transcript set club=club, dacs=dacs, overallOpinion=overallOpinion where id=id", new Object[]{transcriptDto.getClub(), transcriptDto.getDacs(), transcriptDto.getOverallOpinion(), id});
        } else {
            transcriptRepository.save(Transcript.builder()
                    .user(user)
                    .grade(transcriptDto.getGrade())
                    .club(transcriptDto.getClub())
                    .dacs(transcriptDto.getDacs())
                    .overallOpinion(transcriptDto.getOverallOpinion()).build()
            );
        }
    }

    /* 생활기록부 가져오기 */
    public Transcript getStudentTranscript(User user) {
        Transcript transcript = transcriptRepository.findByUser(user);
        return transcript;
    }

    /* 생활기록부 학년 별로 가져오기 */
    public Transcript getStudentTranscriptByGrade(Integer grade,User user){
        Transcript transcript = transcriptRepository.findByGradeAndUser(grade, user);
        return transcript;
    }
}
