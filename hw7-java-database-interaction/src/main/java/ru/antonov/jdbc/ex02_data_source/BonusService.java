package ru.antonov.jdbc.ex02_data_source;

public class BonusService {
    private DataSource dataSource;

    public BonusService(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
