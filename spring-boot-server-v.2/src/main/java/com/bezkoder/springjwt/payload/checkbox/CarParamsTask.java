package com.bezkoder.springjwt.payload.checkbox;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarParamsTask {
    String name;
    boolean completed;
    CarParamsTask[] subtasks;
}
