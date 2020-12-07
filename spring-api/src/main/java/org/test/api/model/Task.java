package org.test.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Task {
    @Id
    private String name;
    private String description;
    private String inputp;
    private String outputp;
}
