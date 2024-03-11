package com.example.tutorial.dto.Report;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OverallReportDTO {
    private LocalDate fromDate;
    private LocalDate toDate;
    private int totalRevenue;
    private int totalOrders;
    private int totalProductsSold;
}
