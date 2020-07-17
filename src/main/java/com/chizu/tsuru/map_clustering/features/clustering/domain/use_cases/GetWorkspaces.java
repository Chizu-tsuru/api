package com.chizu.tsuru.map_clustering.features.clustering.domain.use_cases;

import com.chizu.tsuru.map_clustering.core.useCases.NoParams;
import com.chizu.tsuru.map_clustering.core.useCases.UseCase;
import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Workspace;
import com.chizu.tsuru.map_clustering.features.clustering.domain.repository.ClusteringRepository;

import java.util.List;

public class GetWorkspaces implements UseCase<List<Workspace>, NoParams> {

    private final ClusteringRepository clusteringRepository;

    public GetWorkspaces(ClusteringRepository clusteringRepository) {
        this.clusteringRepository = clusteringRepository;
    }

    @Override
    public List<Workspace> execute(NoParams noParams) {
        return clusteringRepository.getWorkspaces();
    }
}
