package com.diegojacober.desafiopicpay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.diegojacober.desafiopicpay.domain.user.User;
import com.diegojacober.desafiopicpay.dtos.request.NotificationDTO;

@Service
public class NotificationService {
    
    @Autowired
    private RestTemplate restTemplate;

    @Value("${services.emailService.url}")
    private String emailServiceUrl;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        ResponseEntity<String> notificationResponse = restTemplate.postForEntity(emailServiceUrl, notificationRequest,String.class); 

        if(notificationResponse.getStatusCode() != HttpStatus.OK) {
            System.out.println("Erro ao enviar notificação");
            throw new Exception("Serviço de notificação offline");
        }
    }
}
