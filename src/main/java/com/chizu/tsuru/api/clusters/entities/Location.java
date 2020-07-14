package com.chizu.tsuru.api.clusters.entities;

import com.chizu.tsuru.api.clusters.dto.GetLocationDTO;
import com.chizu.tsuru.api.features.workspace.data.models.TagModel;
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
