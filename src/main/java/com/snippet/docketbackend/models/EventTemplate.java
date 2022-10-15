package com.snippet.docketbackend.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(nullable = false)
    private String type;

    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private User user;

    @Column(nullable = false)
    private String linkName;

    private String eventColor = "#00AA8D";

    @Column(nullable = false)
    private Boolean isEnabled = false;

    // TODO: relation to availab table
    @OneToOne(cascade = CascadeType.ALL)
    private Availability availability;

    // TODO: relation to google meet table
    @OneToOne(cascade = CascadeType.ALL)
    private GoogleMeet googleMeet;
}
