package org.test.api.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Solution extends IDObject {
    private String name;
    @ManyToOne
    private Task task;
    @Lob
    private String code;
    @Column(nullable = false, updatable = false)
    private Timestamp created;
    private boolean success;

    @PrePersist
    void onCreate() {
        Timestamp stamp = new Timestamp((new Date()).getTime());
		this.setCreated(stamp);
	}
}
