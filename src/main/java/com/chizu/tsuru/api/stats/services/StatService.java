package com.chizu.tsuru.api.stats.services;

import com.chizu.tsuru.api.stats.dto.CreateStatDTO;
import com.chizu.tsuru.api.stats.entities.Stat;
import com.chizu.tsuru.api.stats.repositories.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StatService {

    private final StatRepository statRepository;

    @Autowired
    public StatService(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    public Page<Stat> getStats(int page, int limit, String sortedColumn, String sortedDirection) {
        Sort sort;
        if( sortedDirection.equals("asc")) {
            sort = Sort.by(sortedColumn).ascending();
        } else {
            sort = Sort.by(sortedColumn).descending();
        }
        Pageable pagination = PageRequest.of(page, limit, sort);
        return this.statRepository.findAll(pagination);
    }

    public Stat createStat(CreateStatDTO statDTO) {
        var stat = Stat.builder()
                .date(statDTO.getDate())
                .executionTime(statDTO.getExecutionTime())
                .origin(statDTO.getOrigin())
                .request(statDTO.getRequest())
                .totalResult(statDTO.getTotalResult())
                .build();

        return this.statRepository.save(stat);
    }
}
