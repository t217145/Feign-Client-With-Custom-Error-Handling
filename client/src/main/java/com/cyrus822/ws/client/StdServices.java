package com.cyrus822.ws.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "StudentWebService", url = "http://localhost:8081")
public interface StdServices {
    @GetMapping("/index")
    List<Students> getStudents();

    @PostMapping("/add")
    Students addStudents(@RequestBody Students newStd);
}