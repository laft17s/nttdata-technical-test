package com.nttdata.account.infrastructure.controller;

import com.nttdata.account.application.dto.AccountStatementReportDTO;
import com.nttdata.account.application.service.ReportService;
import com.nttdata.common.constants.ApiConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Controlador REST para reports
 * Endpoint: /api/reports
 * Funcionalidad F4: Report de Estado de Account
 * Adaptado a Spring WebFlux
 */
@RestController
@RequestMapping(ApiConstants.REPORTES_PATH)
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    
    private final ReportService reporteService;
    
    /**
     * GET /api/reports?fecha=2022-02-08,2022-02-10&cliente={clientId}
     * Genera reporte de estado de cuenta
     */
    @GetMapping
    public Mono<AccountStatementReportDTO> generarReport(
            @RequestParam(name = "fecha") String dateRange,
            @RequestParam(name = "cliente") String clientId) {
        
        log.info("GET /reports - Generando reporte para cliente: {} con rango: {}", clientId, dateRange);
        
        // Parsear rango de fechas (formato: "2022-02-08,2022-02-10")
        String[] fechas = dateRange.split(",");
        if (fechas.length != 2) {
            throw new IllegalArgumentException("El formato de fecha debe ser: YYYY-MM-DD,YYYY-MM-DD");
        }
        
        LocalDateTime startDate = LocalDateTime.parse(fechas[0] + "T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse(fechas[1] + "T23:59:59");
        
        return reporteService.generarReport(clientId, startDate, endDate);
    }
}
