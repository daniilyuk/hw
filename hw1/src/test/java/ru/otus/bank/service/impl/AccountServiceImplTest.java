package ru.otus.bank.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.bank.dao.AccountDao;
import ru.otus.bank.entity.Account;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.exception.AccountException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)

public class AccountServiceImplTest {
    @Mock
    AccountDao accountDao;

    @InjectMocks
    AccountServiceImpl accountServiceImpl;


    @Test
    void testTransfer() {
        Account sourceAccount = new Account();
        sourceAccount.setAmount(new BigDecimal(100));

        Account destinationAccount = new Account();
        destinationAccount.setAmount(new BigDecimal(10));

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(eq(2L))).thenReturn(Optional.of(destinationAccount));

        accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));

        assertEquals(new BigDecimal(90), sourceAccount.getAmount());
        assertEquals(new BigDecimal(20), destinationAccount.getAmount());
    }

    @Test
    void testSourceNotFound() {
        when(accountDao.findById(any())).thenReturn(Optional.empty());

        AccountException result = assertThrows(AccountException.class, () ->
                accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10)));
        assertEquals("No source account", result.getLocalizedMessage());
    }


    @Test
    void testTransferWithVerify() {
        Account sourceAccount = new Account();
        sourceAccount.setAmount(new BigDecimal(100));
        sourceAccount.setId(1L);
        Account destinationAccount = new Account();
        destinationAccount.setAmount(new BigDecimal(10));
        destinationAccount.setId(2L);
        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(eq(2L))).thenReturn(Optional.of(destinationAccount));
        ArgumentMatcher<Account> sourceMatcher =
                argument -> argument.getId().equals(1L) && argument.getAmount().equals(new BigDecimal(90));
        ArgumentMatcher<Account> destinationMatcher =
                argument -> argument.getId().equals(2L) && argument.getAmount().equals(new BigDecimal(20));
        accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));
        verify(accountDao).save(argThat(sourceMatcher));
        verify(accountDao).save(argThat(destinationMatcher));
    }


    @Test
    void testAddAccount() {
        Agreement agreement = new Agreement();
        agreement.setId(1L);
        when(accountDao.save(any(Account.class))).thenAnswer(invocation -> {
            Account account = invocation.getArgument(0);
            account.setId(123L); // Simulate saving with generated ID
            return account;
        });
        Account savedAccount = accountServiceImpl.addAccount(agreement, "123456", 1, BigDecimal.valueOf(100));
        assertNotNull(savedAccount);
        assertEquals(123L, savedAccount.getId());
        assertEquals(agreement.getId(), savedAccount.getAgreementId());
        assertEquals("123456", savedAccount.getNumber());
        assertEquals(1, savedAccount.getType());
        assertEquals(BigDecimal.valueOf(100), savedAccount.getAmount());
    }

    @Test
    void testGetAccounts() {
        List<Account> mockAccounts = new ArrayList<>();
        mockAccounts.add(new Account());
        mockAccounts.add(new Account());
        when(accountDao.findAll()).thenReturn(mockAccounts);
        List<Account> accounts = accountServiceImpl.getAccounts();
        assertEquals(2, accounts.size());
    }

    @Test
    void testCharge() {
        Account account = new Account();
        account.setId(1L);
        account.setAmount(BigDecimal.valueOf(100));
        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(account));
        assertTrue(accountServiceImpl.charge(1L, BigDecimal.TEN));
        assertEquals(BigDecimal.valueOf(90), account.getAmount());
    }
}
