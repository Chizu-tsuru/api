package com.chizu.tsuru.map_clustering.stats.services;

import com.chizu.tsuru.map_clustering.stats.dto.GetStatDTO;
import com.chizu.tsuru.map_clustering.stats.entities.Stat;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class StatDTOService {

    public GetStatDTO toResponse(Stat stat) {
        return GetStatDTO.builder()
                .date(stat.getDate())
                .executionTime(stat.getExecutionTime())
                .origin(stat.getOrigin())
                .request(stat.getRequest())
                .totalResult(stat.getTotalResult())
                .build();
    }

    public Page<GetStatDTO> toResponse(Page<Stat> stats) {
        return stats.map(this::toResponse);
    }
}
