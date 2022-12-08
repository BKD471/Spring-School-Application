package com.example.school.model;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "holidays")
public class HoliDay extends BaseEntity {
    @Id
    private String day;
    private String reason;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        FESTIVAL, FEDERAL
    }
}
