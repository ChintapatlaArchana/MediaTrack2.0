package com.cts.service;

import com.cts.dto.EngagementReportRequestDTO;
import com.cts.mapper.EngagementReportMapper;
import com.cts.model.EngagementReport;
import com.cts.repository.EngagementReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EngagementReportService {
    public EngagementReportService(EngagementReportRepository engagementReportRepository, EngagementReportMapper engagementReportMapper) {
        this.engagementReportRepository = engagementReportRepository;
        this.engagementReportMapper = engagementReportMapper;
    }


    private final EngagementReportRepository engagementReportRepository;


    private final EngagementReportMapper engagementReportMapper;

    public EngagementReport generateEngagementReport(EngagementReportRequestDTO dto){
        EngagementReport report = engagementReportMapper.toEntity(dto);
        return engagementReportRepository.save(report);
    }

    public List<EngagementReport> getAllEngagementReports(){
        return engagementReportRepository.findAll();
    }

    public void deleteEngagementReport(Long id){
        if(engagementReportRepository.existsById(id)){
            engagementReportRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Engagement report does not exist with id: "+id);
        }
    }



}
