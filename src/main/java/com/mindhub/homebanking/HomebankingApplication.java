package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@SpringBootApplication
public class HomebankingApplication {



	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);


		System.out.println("tengo miedo");

	}

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {



			LocalDate hoy = LocalDate.now();
			LocalDateTime today = LocalDateTime.now();


			Client client1 = new Client("melba", "merel", "melba@mindhub.com", passwordEncoder.encode("melba"));
			clientRepository.save(client1);

			Account account1 = new Account(client1 ,"VIN001", LocalDateTime.now(), 5000, AccountType.CORRIENTE );
			accountRepository.save(account1);

			Account account2 = new Account(client1 ,"VIN002", today.plusDays(1), 7500, AccountType.AHORRO );
			accountRepository.save(account2);

			Client client2 = new Client("Elias", "Velazques", "EliasVelazques@gmail.com", passwordEncoder.encode("quieroDormir"));
			clientRepository.save(client2);

			Client client3 = new Client("ana", "vivas", "anavivas@pepito.com", passwordEncoder.encode("anatoli"));
			clientRepository.save(client3);

			Client admin = new Client("Seba", "Medina", "seba@admin.com", passwordEncoder.encode("hola"));
			clientRepository.save(admin);


			Account account3 = new Account(client2, "VIN003", LocalDateTime.now(), 10000, AccountType.CORRIENTE  );
			accountRepository.save(account3);

			Transaction transaction1 = new Transaction(account1, TransactionType.DEBITO, -5000, "Comida para gatos", LocalDateTime.now());
			transactionRepository.save(transaction1);

			Transaction transaction2 = new Transaction(account1, TransactionType.CREDITO, 10000,"Acciones en carrera de piedras" , today.plusDays(-1));
			transactionRepository.save(transaction2);

			Transaction transaction3 = new Transaction(account1, TransactionType.DEBITO, -12000,"Pedidos ya" , today.plusDays(5));
			transactionRepository.save(transaction3);

			Transaction transaction4 = new Transaction(account2, TransactionType.CREDITO, 50000, "juegos de azar y mujersuelas", today.plusDays(-1));
			transactionRepository.save(transaction4);

			Transaction transaction5 = new Transaction(account2, TransactionType.DEBITO, -100000, "Loteria", LocalDateTime.now());
			transactionRepository.save(transaction5);

			List<Integer> paymentHipotecario = List.of(12, 24, 36, 48, 60);
			List<Integer> paymentPersonal = List.of(6,12,24);
			List<Integer> paymentAutomotriz = List.of(6,12,24,36);

			Loan loanHipotecario = new Loan("Hipotecario",500000, paymentHipotecario, 0.18);
			loanRepository.save(loanHipotecario);

			Loan loanPersonal = new Loan("Personal", 100000, paymentPersonal, 0.20);
			loanRepository.save(loanPersonal);

			Loan loanAutomotriz = new Loan("Automotriz", 300000, paymentAutomotriz, 0.25);
			loanRepository.save(loanAutomotriz);

			ClientLoan prestamo1 = new ClientLoan(client1, loanHipotecario, 450000, 60);
			clientLoanRepository.save(prestamo1);

			ClientLoan prestamo2 = new ClientLoan(client1, loanPersonal, 50000, 12);
			clientLoanRepository.save(prestamo2);

			ClientLoan prestamo3 = new ClientLoan(client2, loanPersonal, 100000, 24);
			clientLoanRepository.save(prestamo3);

			ClientLoan prestamo4 = new ClientLoan(client2, loanAutomotriz, 200000,36 );
			clientLoanRepository.save(prestamo4);

			// Cards guardadas //

			Card card1 = new Card(client1, CardType.DEBIT ,CardColor.GOLD, LocalDate.now(), hoy.plusYears(5), 355, "3444-3535-8511");
			cardRepository.save(card1);

			Card card2 = new Card(client1, CardType.CREDIT, CardColor.TITANIUM, LocalDate.now(), hoy.plusYears(5), 764, "4236-5532-2987");
			cardRepository.save(card2);

			Card card3 = new Card(client1, CardType.CREDIT, CardColor.SILVER, LocalDate.now(), hoy.plusYears(5), 543, "4329-7528-9876");
			cardRepository.save(card3);

			Card card4 = new Card(client2, CardType.DEBIT, CardColor.SILVER, LocalDate.now(), hoy.plusYears(3), 423, "4329-4583-4832");
			cardRepository.save(card4);

		};
	}

}
