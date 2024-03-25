package ru.otus.bank.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.bank.entity.Account;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.AccountService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentProcessorImplTest {

    @Mock
    AccountService accountService;

    @InjectMocks
    PaymentProcessorImpl paymentProcessor;

    @Test
    void testTransfer() {
        Agreement sourceAgreement = new Agreement();
        sourceAgreement.setId(1L);

        Agreement destinationAgreement = new Agreement();
        destinationAgreement.setId(2L);

        Account sourceAccount = new Account();
        sourceAccount.setAmount(BigDecimal.TEN);
        sourceAccount.setType(0);

        Account destinationAccount = new Account();
        destinationAccount.setAmount(BigDecimal.ZERO);
        destinationAccount.setType(0);

        when(accountService.getAccounts(argThat(argument -> argument != null && argument.getId() == 1L))).thenReturn(List.of(sourceAccount));

        when(accountService.getAccounts(argThat(argument -> argument != null && argument.getId() == 2L))).thenReturn(List.of(destinationAccount));

        paymentProcessor.makeTransfer(sourceAgreement, destinationAgreement,
                0, 0, BigDecimal.ONE);

        verify(accountService).getAccounts(argThat(agreement ->
                agreement != null && agreement.getId() == 1L));
        verify(accountService).getAccounts(argThat(agreement ->
                agreement != null && agreement.getId() == 2L));
        verify(accountService).makeTransfer(sourceAccount.getId(), destinationAccount.getId(), BigDecimal.ONE);
    }

    @Test
    void testMakeTransferWithComission() {
        Agreement sourceAgreement = new Agreement();
        sourceAgreement.setId(1L);
        Agreement destinationAgreement = new Agreement();
        destinationAgreement.setId(2L);
        Account sourceAccount = new Account();
        sourceAccount.setId(10L);
        sourceAccount.setAmount(BigDecimal.TEN);
        sourceAccount.setType(0);
        Account destinationAccount = new Account();
        destinationAccount.setId(20L);
        destinationAccount.setAmount(BigDecimal.ZERO);
        destinationAccount.setType(0);
        BigDecimal comissionPercent = BigDecimal.valueOf(0.01);
        when(accountService.getAccounts(argThat(agreement ->
                agreement != null && agreement.getId() == 1L)))
                .thenReturn(List.of(sourceAccount));
        when(accountService.getAccounts(argThat(agreement ->
                agreement != null && agreement.getId() == 2L)))
                .thenReturn(List.of(destinationAccount));
        paymentProcessor.makeTransferWithComission(sourceAgreement, destinationAgreement,
                0, 0, BigDecimal.ONE, comissionPercent);
        verify(accountService).charge(10L, BigDecimal.valueOf(0.01).negate());
        verify(accountService).makeTransfer(10L, 20L, BigDecimal.ONE);
    }
}
