package com.snippet.docketbackend.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_template")
@NoArgsConstructor
@Getter
@Setter
public class EventTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String useremail;

    @Column(nullable = false)
    private String linkName;

    private String eventColor = "#00AA8D";

    @Column(nullable = false)
    private Boolean isEnabled = false;

    // TODO: relation to availab table
    @OneToMany(cascade = CascadeType.ALL)
    private List<Availability> availability;

    // TODO: relation to google meet table
    @OneToOne(cascade = CascadeType.ALL)
    private GoogleMeet googleMeet;
}
