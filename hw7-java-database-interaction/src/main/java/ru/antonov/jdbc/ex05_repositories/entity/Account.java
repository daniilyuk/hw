package ru.antonov.jdbc.ex05_repositories.entity;

import ru.antonov.jdbc.ex05_repositories.annotation.RepositoryField;
import ru.antonov.jdbc.ex05_repositories.annotation.RepositoryFieldName;
import ru.antonov.jdbc.ex05_repositories.annotation.RepositoryIdField;
import ru.antonov.jdbc.ex05_repositories.annotation.RepositoryTable;

@RepositoryTable(title = "accounts")
public class Account {
    @RepositoryIdField
    @RepositoryField
    private Long id;

    @RepositoryField
    private Long amount;

    @RepositoryField // TODO (name = "tp");
    @RepositoryFieldName(title = "account_type")
    private String acctType;

    @RepositoryField
    private String status;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public Long getAmount() {
        return amount;
    }

    public String getAcctType() {
        return acctType;
    }

    public String getStatus() {
        return status;
    }

    public Account(Long amount, String accountType, String status) {
        this.amount = amount;
        this.acctType = accountType;
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public void setAcctType(String acctType) { this.acctType = acctType;}

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", amount=" + amount +
                //  ", accountType='" + acctype + '\'' +
                ", accountType='" + acctType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

