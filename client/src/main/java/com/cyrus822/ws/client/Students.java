package com.cyrus822.ws.client;

import java.io.Serializable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Students implements Serializable{
    private int stdId;

    @NotEmpty(message = "Student Code must be provided!")
    @Size(max = 8, min = 8, message = "Student Code must be 8 characters")
    private String stdNo;
    
    @NotEmpty(message = "Student Name must be provided!")
    @Size(min = 1, max = 50, message = "Student name must less then 50 characters")
    private String name;
}