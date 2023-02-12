package com.cyrus822.ws.ws;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    private List<Students> allStudents = new ArrayList<>();

    @GetMapping({"","/","/index"})
    public List<Students> index(){
        return allStudents;
    }

    @PostMapping("/add")
    public ResponseEntity<Students> add(@RequestBody Students newStd) throws CustomException{
        if(allStudents.stream().anyMatch(s -> s.getStdNo().equalsIgnoreCase(newStd.getStdNo()))){
            throw new CustomException("[TestController]:[add]", String.format("Student Code [%s] already exists!", newStd.getStdNo()));
        }
        newStd.setStdId(allStudents.size() + 1);
        allStudents.add(newStd);
        return ResponseEntity.ok(newStd);
    }
}
