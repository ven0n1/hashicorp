package com.vault.hashicorp;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Repository {

    public static final String SQL = "select * from dao_class";
    public static final String INSERT_SQL = """
            INSERT INTO public.dao_class(
            \t"ACCOUNTNUMBER", epk_id, "FieldOne", "FIELD_TWO")
            \tselect md5(random()::text), md5(random()::text), md5(random()::text), md5(random()::text) from generate_Series(1,5) s""";
    //    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @SneakyThrows
    public List<DaoClass> selectAll() {
        BeanPropertyRowMapper<DaoClass> mapper = BeanPropertyRowMapper.newInstance(DaoClass.class);
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SQL);
            List<DaoClass> daoClasses = new ArrayList<>();
            int row = 0;
            while (resultSet.next()) {
                row++;
                daoClasses.add(mapper.mapRow(resultSet, row));
            }
            return daoClasses;
        }
//        return jdbcTemplate.query(SQL, mapper);
    }

    public void insert() {
//        jdbcTemplate.update(INSERT_SQL);
    }
}
