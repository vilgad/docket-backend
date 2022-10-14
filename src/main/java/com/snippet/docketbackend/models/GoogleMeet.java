package com.snippet.docketbackend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "google_meet")
@NoArgsConstructor
@Getter
@Setter
public class GoogleMeet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean guestsCanInviteOthers = false;

    @Column(nullable = false)
    private Boolean guestsCanModify = false;

    @Column(nullable = false)
    private Boolean guestsCanSeeOtherGuests = true;
}
