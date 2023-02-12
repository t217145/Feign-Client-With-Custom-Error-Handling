package com.cyrus822.ws.client;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.validation.Valid;

@Controller
public class TestController {
    
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private StdServices svc;
    
    @GetMapping({"","/","/index"})
    public String index(ModelMap m){
        m.addAttribute("allStd", svc.getStudents());
        return "index";
    }

    @GetMapping("/create")
    public String create(ModelMap m){
        m.addAttribute("newStd", new Students());
        return "create";
    }

    @PostMapping("/create")
    @SuppressWarnings("unchecked")
    public String create(ModelMap m, @ModelAttribute("newStd") @Valid Students newStd, BindingResult result){       
        boolean hasError = result.hasErrors(); //local validation
        try{
            if(!hasError){
                svc.addStudents(newStd);
            }
        }catch(FeignException fe){ //WS validation
            hasError = true;
            logger.error("Error when calling [Add] : {}", fe.getMessage());
            String resp = fe.contentUTF8();
            Map<String, String> custError = new HashMap<>();
            try {
                custError = new ObjectMapper().readValue(resp, Map.class);
            } catch (JsonProcessingException e) {
                logger.error("Error when calling [Add] : Error Response cannot be converted {}", resp);
                e.printStackTrace();
            }
            result.reject("err", custError.getOrDefault("errMsg", "Error Occur"));
        }

        if(hasError){
            m.addAttribute("newStd", newStd);
            return "create";
        }
        return "redirect:/index";
    }
}
