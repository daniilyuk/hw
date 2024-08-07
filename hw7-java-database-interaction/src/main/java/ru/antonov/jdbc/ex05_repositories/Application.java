package ru.antonov.jdbc.ex05_repositories;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.antonov.jdbc.ex05_repositories.config.DataSource;
import ru.antonov.jdbc.ex05_repositories.config.DbMigrator;
import ru.antonov.jdbc.ex05_repositories.entity.Account;
import ru.antonov.jdbc.ex05_repositories.entity.User;
import ru.antonov.jdbc.ex05_repositories.repository.AbstractRepository;

import java.util.List;

public class Application {
    // Домашнее задание:
    // - Реализовать класс DbMigrator - он должен при старте создавать все необходимые таблицы из файла init.sql
    // Доработать AbstractRepository
    // - Доделать findById(id), findAll(), update(), deleteById(id), deleteAll()
    // - Сделать возможность указывать имя столбца таблицы для конкретного поля (например, поле accountType маппить на столбец с именем account_type)
    // - Добавить проверки, если по какой-то причине невозможно проинициализировать репозиторий, необходимо бросать исключение, чтобы
    // программа завершила свою работу (в исключении надо объяснить что сломалось)
    // - Работу с полями объектов выполнять только через геттеры/сеттеры

    public static final Logger logger = LogManager.getLogger(Application.class.getName());
    public static void main(String[] args) {
        DataSource dataSource = null;
        try {
            dataSource = new DataSource("jdbc:h2:mem:testdb");
            dataSource.connect();

            DbMigrator dbMigrator = new DbMigrator(dataSource);
            dbMigrator.migrate();

            AbstractRepository<User> repository = new AbstractRepository<>(dataSource, User.class);
            User user = new User("bob", "123", "bob");
            User user1 = new User("ken", "321", "canon");
            repository.create(user);
            repository.create(user1);
            List<User> users = repository.findAll();
            logger.info("All users: " + (!users.isEmpty() ? users.toString() : "No users!"));
            logger.info("Searching for user 2...");
            User user2 = repository.findById(2L);
            logger.info("User 2: " + (user2 != null ? user2.toString() : "Not found!"));
            logger.info("Updating user 2...");
            if (user2 != null) {
                user2.setNickname("Vasya");
                user2.setPassword("777");
                repository.update(user2);
                user2 = repository.findById(2L);
                logger.info("User 2: " + (user2 != null ? user2.toString() : "Not found!"));
            }
            logger.info("Deleted user 2?: " + repository.deleteById(2L));
            users = repository.findAll();
            logger.info("All users: " + (!users.isEmpty() ? users.toString() : "No users!"));
            logger.info("Deleting all users...");
            repository.deleteAll();
            users = repository.findAll();
            logger.info("Remaining users: " + (!users.isEmpty() ? (users.toString() + "\n") : "No users!\n"));

            AbstractRepository<Account> accountRepository = new AbstractRepository<>(dataSource, Account.class);
            Account account = new Account(100L, "credit", "blocked");
            accountRepository.create(account);
            Account account2 = new Account(300L, "card", "active");
            accountRepository.create(account2);
            List<Account> accounts = accountRepository.findAll();
            logger.info("All accounts: " + (!accounts.isEmpty() ? accounts.toString() : "No accounts!"));
            logger.info("Searching for account 2...");
            account2 = accountRepository.findById(2L);
            logger.info("Account 2: " + (account2 != null ? account2.toString() : "Not found!"));
            logger.info("Deleting account 1...");
            accountRepository.deleteById(2L);
            logger.info("Deleted account 1?: " + accountRepository.deleteById(1L));
            accounts = accountRepository.findAll();
            logger.info("All accounts: " + (!accounts.isEmpty() ? accounts.toString() : "No accounts!"));

        } catch (Exception e) {
            logger.error("An error occurred: ", e);
        } finally {
            if (dataSource != null) {
                dataSource.disconnect();
            }
        }
    }
}