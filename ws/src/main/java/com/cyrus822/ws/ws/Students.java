package com.cyrus822.ws.ws;

import java.io.Serializable;
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
    private String stdNo;
    private String name;
}
