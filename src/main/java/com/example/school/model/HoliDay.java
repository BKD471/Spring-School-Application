package com.example.school.model;

import lombok.*;

//@Getter
//@Setter
//@AllArgsConstructor
@Data
public class HoliDay  extends BaseEntity{

    private  String day;
    private  String reason;
    private  Type type;

    public enum Type{
        FESTIVAL,FEDERAL
    }


}
