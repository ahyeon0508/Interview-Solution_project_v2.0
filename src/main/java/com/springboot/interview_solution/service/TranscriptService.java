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

    public void setStudentTranscript(TranscriptDto transcriptDto, User user) {
        if(transcriptRepository.findByGradeAndUser(transcriptDto.getGrade(),user).isPresent()) {
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

    public Transcript getStudentTranscript(User user) {
        Transcript transcript = transcriptRepository.findTranscriptByUser(user).orElse(new Transcript(user, null, null, null, null));
        return transcript;
    }
}
