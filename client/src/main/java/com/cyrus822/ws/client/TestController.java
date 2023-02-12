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
    public String create(ModelMap m, @ModelAttribute("newStd") @Valid Students newStd, BindingResult result){       
        try{
            if(!result.hasErrors()){ //has local validation error (form validation)
                svc.addStudents(newStd); //consume the HTTP POST Web Services
                return "redirect:/index";//Success and return, to prevent execute remaining code
            }
        }catch(FeignException fe){ //Web Services validation occur error
            logger.error("Error when calling [Add] : {}", fe.getMessage());//Log the full error message
            //Get the response body in UTF8 String format and deserialize to Map<String, String>
            Map<String, String> custError = convertJsonToObect(fe.contentUTF8());
            //Return the error message and display in  general error message in view
            result.reject("error", custError.getOrDefault("errMsg", "Error Occur"));
        }

        m.addAttribute("newStd", newStd);
        return "create";
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> convertJsonToObect(String json){
        Map<String, String> rtn = new HashMap<>();
        try {
            //Do the convertion
            rtn = new ObjectMapper().readValue(json,  Map.class);
        } catch (JsonProcessingException e) {
            logger.error("Error Response cannot be converted {}", json);
            e.printStackTrace();
        }
        return rtn;
    }
}
