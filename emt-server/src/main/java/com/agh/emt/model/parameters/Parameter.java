package com.agh.emt.model.parameters;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document("parameter")
@Data
@AllArgsConstructor
public class Parameter {
    @Id
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String value;

    private String description;

    public Parameter(){}

    public Parameter(String name, String value){
        this.name = name;
        this.value = value;
    };
}
