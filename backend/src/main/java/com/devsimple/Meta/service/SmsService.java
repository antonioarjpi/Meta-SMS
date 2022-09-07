package com.devsimple.Meta.service;

import com.devsimple.Meta.model.Sale;
import com.devsimple.Meta.repository.SaleRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.sid}")
    private String twilioSid;

    @Value("${twilio.key}")
    private String twilioKey;

    @Value("${twilio.phone.from}")
    private String twilioPhoneFrom;

    @Value("${twilio.phone.to}")
    private String twilioPhoneTo;

    private SaleRepository saleRepository;

    public SmsService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public void sendSms(String id) {
        Twilio.init(twilioSid, twilioKey);

        PhoneNumber to = new PhoneNumber(twilioPhoneTo);
        PhoneNumber from = new PhoneNumber(twilioPhoneFrom);

        Sale sale = saleRepository.findById(id).get();

        String date = sale.getDate().getMonthValue() + "/" + sale.getDate().getYear();
        String message = "Vendedor " + sale.getSellerName() + " vendeu no dia" + date + " o total de  R$ " + String.format("%.2f", sale.getAmount());

        Message.creator(to, from, message).create();
    }
}
