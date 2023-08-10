package com.ddukddak.backend.report;

import com.ddukddak.backend.exception.custonException.ReportTimeException;
import com.ddukddak.backend.report.reportedDTO.ReportedDTO;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportedService {

    private final ReportedRepository reportedRepository;
    private final UserRepository userRepository;

    public void report(ReportedDTO reportedDTO) throws ReportTimeException {
        User reporter = userRepository.findByName(reportedDTO.getReporter());
        User reported = userRepository.findByName(reportedDTO.getReportedUser());
        if (reporter.getReportTime().plusMinutes(3).isBefore(LocalDateTime.now())) {
            reporter.setReportTime(LocalDateTime.now());
            reported.setReportNumber(reported.getReportNumber() + 1);
            reported.getReportedMessages().add(ReportedMessage.create(reportedDTO.getAbusedMessage(), reported));
        } else {
            throw new ReportTimeException();
        }
    }
}
