package com.santclear.services;

import org.springframework.mail.SimpleMailMessage;

import com.santclear.domain.Cliente;
import com.santclear.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
