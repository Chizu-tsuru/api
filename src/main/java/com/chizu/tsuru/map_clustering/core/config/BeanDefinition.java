package com.chizu.tsuru.map_clustering.core.config;

import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.repository.ClusterProcessingRepository;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.services.MapService;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.use_cases.GetClusterById;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.use_cases.GetMinimumDistanceAverage;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.use_cases.GetTravelPath;
import com.chizu.tsuru.map_clustering.features.clustering.domain.repository.ClusteringRepository;
import com.chizu.tsuru.map_clustering.features.clustering.domain.use_cases.CreateWorkspace;
import com.chizu.tsuru.map_clustering.features.clustering.domain.use_cases.GetClustersByWorkspace;
import com.chizu.tsuru.map_clustering.features.clustering.domain.use_cases.GetWorkspaceById;
import com.chizu.tsuru.map_clustering.features.clustering.domain.use_cases.GetWorkspaces;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanDefinition {
    private final ClusteringRepository clusteringRepository;
    private final ClusterProcessingRepository clusterProcessingRepository;
    private final MapService mapService;

    public BeanDefinition(ClusteringRepository clusteringRepository, ClusterProcessingRepository clusterProcessingRepository, MapService mapService) {
        this.clusteringRepository = clusteringRepository;
        this.clusterProcessingRepository = clusterProcessingRepository;
        this.mapService = mapService;
    }

    @Bean
    public GetClustersByWorkspace getClustersByWorkspace() {
        return new GetClustersByWorkspace(clusteringRepository);
    }

    @Bean
    public CreateWorkspace createWorkspace() {
        return new CreateWorkspace(clusteringRepository);
    }

    @Bean
    public GetWorkspaceById getWorkspaceById() {
        return new GetWorkspaceById(clusteringRepository);
    }

    @Bean
    public GetWorkspaces getWorkspaces() {
        return new GetWorkspaces(clusteringRepository);
    }
    @Bean
    public GetMinimumDistanceAverage getMinimumDistanceAverage() {
        return new GetMinimumDistanceAverage(clusterProcessingRepository, mapService);
    }
    @Bean
    public GetClusterById getClusterById() {
        return new GetClusterById(clusterProcessingRepository);
    }
    @Bean
    public GetTravelPath getTravelPath() {
        return new GetTravelPath(clusterProcessingRepository, mapService);
    }
}
