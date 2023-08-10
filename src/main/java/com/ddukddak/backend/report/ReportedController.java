package com.ddukddak.backend.report;

import com.ddukddak.backend.exception.custonException.ReportTimeException;
import com.ddukddak.backend.report.reportedDTO.ReportedDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportedController {

    private final ReportedService reportedService;

    @PostMapping("/report")
    public ResponseEntity report(@RequestBody ReportedDTO reportedDTO) throws ReportTimeException {
        reportedService.report(reportedDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
