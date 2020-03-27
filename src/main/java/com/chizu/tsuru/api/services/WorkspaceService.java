package com.chizu.tsuru.api.services;

import com.chizu.tsuru.api.Entities.Workspace;
import com.chizu.tsuru.api.repositories.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Transactional(readOnly = true)
    public List<Workspace> getWorkspaces() {
        return workspaceRepository.findAll();
    }
}
