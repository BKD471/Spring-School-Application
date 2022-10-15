package com.example.school.model;

import lombok.*;

//@Getter
//@Setter
//@AllArgsConstructor
@Data
public class HoliDay {

    private final String day;
    private final String reason;
    private final Type type;

    public enum Type{
        FESTIVAL,FEDERAL
    }


}
