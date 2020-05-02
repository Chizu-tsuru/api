package com.chizu.tsuru.api.clusters.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer TagId;

    @NotNull
    private String name;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tags")
    private List<Location> locations;
}
