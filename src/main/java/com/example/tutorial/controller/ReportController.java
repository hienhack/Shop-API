package com.example.tutorial.controller;

import com.example.tutorial.dto.Report.OverallReportByDayDTO;
import com.example.tutorial.dto.Report.OverallReportDTO;
import com.example.tutorial.dto.Report.ProductStatisticDTO;
import com.example.tutorial.dto.Response.ResponseDTO;
import com.example.tutorial.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/product-statistic")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<ProductStatisticDTO>> getProductStatisticBetween(
            @RequestParam(name = "from", defaultValue = "") String from,
            @RequestParam(name = "to", defaultValue = "") String to
    ) {
        return ResponseDTO.of(reportService.getProductStatistic(getDate(from), getDate(to)));
    }

    @GetMapping("/overall")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<OverallReportDTO> getOverallReportFromDateToDate(
            @RequestParam(name = "from", defaultValue = "") String from,
            @RequestParam(name = "to", defaultValue = "") String to
    ) {
        return ResponseDTO.of(reportService.getOverallReport(getDate(from), getDate(to)));
    }

    @GetMapping("/overall-by-day")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<OverallReportByDayDTO>> getOverallReportByDayFromDateToDate(
            @RequestParam(name = "from", defaultValue = "") String from,
            @RequestParam(name = "to", defaultValue = "") String to
    ) {
        return ResponseDTO.of(reportService.getOverallReportByDay(getDate(from), getDate(to)));
    }

    private LocalDate getDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            return LocalDate.now();
        }
    }
}
