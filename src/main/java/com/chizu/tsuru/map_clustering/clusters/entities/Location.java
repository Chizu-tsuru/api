package com.chizu.tsuru.map_clustering.clusters.entities;

import com.chizu.tsuru.map_clustering.clusters.dto.GetLocationDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer locationId;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cluster_id")
    private Cluster cluster;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tag> tags;

    public GetLocationDTO toResponse() {
        return GetLocationDTO.builder()
                .latitude(latitude)
                .longitude(longitude)
                .tags(tags != null ? tags.stream().map(Tag::getName).collect(Collectors.toList()) : null)
                .build();
    }

}
