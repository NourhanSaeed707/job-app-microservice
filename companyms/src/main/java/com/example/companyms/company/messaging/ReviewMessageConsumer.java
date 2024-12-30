package com.example.companyms.company.messaging;
import com.example.companyms.company.dto.ReviewMessage;
import com.example.companyms.company.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {
    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumerMessage(ReviewMessage reviewMessage) {
        companyService.updateCompany(reviewMessage);
    }

}
