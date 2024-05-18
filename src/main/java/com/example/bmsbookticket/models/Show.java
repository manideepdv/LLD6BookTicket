package com.example.bmsbookticket.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity(name = "shows")
public class Show extends BaseModel{
    private Date startTime;
    private Date endTime;

    @ElementCollection
    private List<Feature> features;

    @ManyToOne
    private Screen screen;
}
