package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MainController {
    private Logger log = LoggerFactory.getLogger(MainController.class);
    ObjectMapper mapper = new ObjectMapper();
    public  long start_time = 0L;

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseDTO postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;

            if (firstDigit == '8') {
                maxLimit = new BigDecimal("2000.00");
            } else if (firstDigit == '9') {
                maxLimit = new BigDecimal("1000.00");
            } else {
                maxLimit = new BigDecimal("10000.00");
            }

            String rqUID = requestDTO.getRqUID();

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setRqUID(rqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(getCurrency(clientId));
            responseDTO.setBalance(generateRandomBalance(maxLimit));
            responseDTO.setMaxLimit(maxLimit);

            return responseDTO;
        } catch (Exception e) {
            // Handle exception
            return null;
        }
    }

    private String getCurrency(String clientId) {
        char firstDigit = clientId.charAt(0);
        return (firstDigit == '8') ? "US" : (firstDigit == '9') ? "EU" : "RUB";
    }

    private String generateRandomBalance(BigDecimal maxLimit) {
        int maxLimitInt = maxLimit.intValue();
        int balance = ThreadLocalRandom.current().nextInt(0, maxLimitInt + 1);
        return String.valueOf(balance);
    }





    @GetMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object getBalances(@RequestBody RequestDTO requestDTO){

        try{
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;

            if (firstDigit == '8'){
                maxLimit = new BigDecimal(2000.00);
            } else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000.00);
            }else {
                maxLimit = new BigDecimal(10000.00);
            }

            String RqUID = requestDTO.getRqUID();

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setRqUID(RqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency("RUB");
            responseDTO.setBalance("900");
            responseDTO.setMaxLimit(maxLimit);

            log.error("*** запрос ***" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("*** ответ ***" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }
}
