package com.example.tutorial.dto.Report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverallReportByDayDTO {
    private LocalDate date;
    private int totalRevenue;
    private int totalOrders;
    private int totalProductsSold;
}
