package com.ddukddak.backend.report.reportedDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportedDTO {
    
    private String reporter;
    private String reportedUser;
    private String abusedMessage;

    public static ReportedDTO create(String reporter, String reportedUser, String abusedMessage) {
        ReportedDTO reportedDTO = new ReportedDTO();
        reportedDTO.setReporter(reporter);
        reportedDTO.setReportedUser(reportedUser);
        reportedDTO.setAbusedMessage(abusedMessage);

        return reportedDTO;
    }
}
