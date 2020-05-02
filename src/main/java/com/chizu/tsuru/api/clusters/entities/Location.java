package com.chizu.tsuru.api.clusters.entities;

import com.chizu.tsuru.api.clusters.dto.GetLocationDTO;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cluster_id")
    private Cluster cluster;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "location_tag",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public GetLocationDTO toResponse() {
        return GetLocationDTO.builder()
                .latitude(latitude)
                .longitude(longitude).build();
    }

}
