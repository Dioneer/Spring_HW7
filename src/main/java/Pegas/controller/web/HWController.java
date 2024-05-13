package Pegas.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HWController {

    @GetMapping("/private-data")
    public String privateData(){
        return "private";
    }

    @GetMapping("/public-data")
    public String publicData(){
        return "public";
    }
}
