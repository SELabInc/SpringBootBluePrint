package com.selab.springbootblueprints.controller;

import com.selab.springbootblueprints.exception.UserNameValidationException;
import com.selab.springbootblueprints.exception.UserPasswordValidationException;
import com.selab.springbootblueprints.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PropertySource("classpath:application-common.properties")
public class IndexController {

    @Setter(onMethod_ = @Autowired)
    private UserService userService;

    @Value("${vworld.api.key}")
    private String vworldApiKey;

    @Value(value = "${geoserver.mapRequest.url}")
    private String geoserverUrl;

    @RequestMapping("/")
    public String getRoot() {
        return "index";
    }

    @RequestMapping("/map")
    public String getMap(Model model) {
        model.addAttribute("vworldApiKey", vworldApiKey);
        model.addAttribute("geoserverUrl", geoserverUrl);
        return "map";
    }
    @GetMapping("/register")
    public void getSignUp(Model model) {

    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<Integer> postUser(String username, String password) {
        int resultValue = 0;

        try {
            userService.addUser(username, password);
        } catch (UserNameValidationException e) {
            resultValue = 1;
        } catch (UserPasswordValidationException e) {
            resultValue = 2;
        }

        return resultValue == 0 ?
                new ResponseEntity<>(resultValue, HttpStatus.CREATED) : new ResponseEntity<>(resultValue, HttpStatus.OK);
    }
}
