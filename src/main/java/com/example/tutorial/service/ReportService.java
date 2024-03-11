package com.example.tutorial.service;

import com.example.tutorial.dto.Product.ProductInListDTO;
import com.example.tutorial.dto.Report.OverallReportByDayDTO;
import com.example.tutorial.dto.Report.OverallReportDTO;
import com.example.tutorial.dto.Report.ProductStatisticDTO;
import com.example.tutorial.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final OrderRepository orderRepository;

    public OverallReportDTO getOverallReport(LocalDate from, LocalDate to) {
        Object[][] statistic = orderRepository.createStatisticFromDateToDate(from, to);
        if (statistic == null) return new OverallReportDTO(from, to,0, 0, 0);
        return new OverallReportDTO(from, to,
                statistic[0][0] != null ? (Integer)statistic[0][0] : 0,
                (Integer)statistic[0][1],
                statistic[0][2] != null ? (Integer)statistic[0][2] : 0
        );
    }

    public List<OverallReportByDayDTO> getOverallReportByDay(LocalDate from, LocalDate to) {
        Object[][] days = orderRepository.createStatisticByDayFromDateToDate(from, to);
        if (days == null) return new ArrayList<>();
        return Stream.of(days).map(info ->
                new OverallReportByDayDTO(((Date)info[0]).toLocalDate(), (int) info[1], (int) info[2], (int) info[3]))
                .toList();
    }

    public List<ProductStatisticDTO> getProductStatistic(LocalDate from, LocalDate to) {
        Object[][] result = orderRepository.createProductStatisticFromDateToDate(from, to);
        if (result == null) return new ArrayList<>();
        return Stream.of(result).map(info ->
                new ProductStatisticDTO(
                        new ProductInListDTO((Integer) info[0], (String) info[1], (String) info[2], (Float) info[3]), (int) info[4])
        ).toList();

    }
}
