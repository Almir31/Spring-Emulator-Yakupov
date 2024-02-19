package com.example.newMock3.Controller;

import com.example.newMock3.Model.RequestDTO;
import com.example.newMock3.Model.ResponceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.math.BigDecimal;


@RestController

public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping (
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;
            String currency;
            BigDecimal balance;
            String RqUID = requestDTO.getRqUID();

            if(firstDigit == '8'){
                maxLimit = new BigDecimal(2000);
                currency = new String("US");
                balance = new BigDecimal(Math.round(Math.random()*2000));
            }
            else if (firstDigit == '9'){
                maxLimit = new BigDecimal(1000);
                currency = new String("EU");
                balance = new BigDecimal(Math.round(Math.random()*1000));
            }
            else {
                maxLimit = new BigDecimal(10000);
                currency = new String("RUB");
                balance = new BigDecimal(Math.round(Math.random()*10000));
            }

            ResponceDTO responceDTO = new ResponceDTO();

            responceDTO.setRqUID(RqUID);
            responceDTO.setClientId(clientId);
            responceDTO.setAccount(requestDTO.getAccount());
            responceDTO.setCurrency(currency);
            responceDTO.setBalance(balance);
            responceDTO.setMaxLimit(maxLimit);

            log.error("*********** RequestDTO **************"+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("*********** ResponceDTO **************"+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responceDTO));

            return responceDTO;
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
