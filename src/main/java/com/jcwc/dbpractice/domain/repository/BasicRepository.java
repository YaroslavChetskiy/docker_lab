package com.jcwc.dbpractice.domain.repository;

import com.jcwc.dbpractice.domain.sqlRequests.BaseSqlRequest;
import com.jcwc.dbpractice.dto.CustomerOrderDTO;
import com.jcwc.dbpractice.dto.RareMagazinePurchaseDTO;
import com.jcwc.dbpractice.dto.StoreCustomerCountDTO;
import com.jcwc.dbpractice.entity.Customer;
import com.jcwc.dbpractice.entity.Magazine;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BasicRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Magazine> getMagazinesByCustomerId(Long customerId) {
        return jdbcTemplate.query(
                BaseSqlRequest.GET_MAGAZINES_BY_CUSTOMER_ID,
                new BeanPropertyRowMapper<>(Magazine.class),
                customerId
        );
    }

    public String getMagazineTitleByIsbn(String isbn) {
        try {
            return jdbcTemplate.queryForObject(
                    BaseSqlRequest.GET_MAGAZINE_TITLE_BY_ISBN,
                    String.class,
                    isbn
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<String> getIsbnByMagazineTitle(String title) {
        try {
            return jdbcTemplate.query(
                    BaseSqlRequest.GET_ISBN_BY_MAGAZINE_TITLE,
                    (rs, rowNum) -> rs.getString("isbn"),
                    title
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public List<LocalDate> getOrderDateByMagazineTitle(String magazineTitle) {
        return jdbcTemplate.query(
                BaseSqlRequest.GET_ORDER_DATE_BY_MAGAZINE_TITLE,
                (rs, rowNum) -> rs.getDate("order_date").toLocalDate(),
                magazineTitle
        );
    }

    public List<Customer> getCustomersWithOrdersOlderThanOneMonth() {
        return jdbcTemplate.query(
                BaseSqlRequest.GET_CUSTOMERS_WITH_ORDERS_OLDER_THAN_ONE_MONTH,
                new BeanPropertyRowMapper<>(Customer.class)
        );
    }

    public List<CustomerOrderDTO> getCustomersWithOrdersOlderThanOneMonthWithDate() {
        return jdbcTemplate.query(
                BaseSqlRequest.GET_CUSTOMERS_WITH_ORDERS_OLDER_THAN_ONE_MONTH_WITH_DATE,
                (rs, rowNum) -> {
                    Customer customer = new Customer();
                    customer.setId(rs.getLong("id"));
                    customer.setEmail(rs.getString("email"));
                    customer.setFullName(rs.getString("full_name"));
                    customer.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    customer.setAddress(rs.getString("address"));
                    customer.setPhoneNumber(rs.getString("phone_number"));

                    LocalDate orderDate = rs.getDate("order_date").toLocalDate();

                    CustomerOrderDTO dto = new CustomerOrderDTO();
                    dto.setCustomer(customer);
                    dto.setOrderDate(orderDate);

                    return dto;
                }
        );
    }

    public List<RareMagazinePurchaseDTO> getCustomersOfRarestMagazines() {
        return jdbcTemplate.query(
                BaseSqlRequest.GET_CUSTOMERS_OF_RAREST_MAGAZINES,
                (rs, rowNum) -> {
                    RareMagazinePurchaseDTO dto = new RareMagazinePurchaseDTO();
                    dto.setStoreId(rs.getInt("store_id"));
                    dto.setStoreName(rs.getString("store_name"));
                    dto.setMagazineId(rs.getInt("magazine_id"));
                    dto.setMagazineTitle(rs.getString("magazine_title"));
                    dto.setCustomerId(rs.getLong("customer_id"));
                    dto.setCustomerFullName(rs.getString("customer_full_name"));
                    return dto;
                }
        );
    }

    public List<StoreCustomerCountDTO> getCustomerCountsByStore() {
        return jdbcTemplate.query(
                BaseSqlRequest.GET_CUSTOMER_COUNT_BY_STORE_ID,
                (rs, rowNum) -> {
                    StoreCustomerCountDTO dto = new StoreCustomerCountDTO();
                    dto.setStoreId(rs.getInt("id"));
                    dto.setStoreName(rs.getString("name"));
                    dto.setCustomerCount(rs.getInt("customer_count"));
                    return dto;
                }
        );
    }

    public Integer countCustomersYoungerThan20() {
        try {
            return jdbcTemplate.queryForObject(
                    BaseSqlRequest.COUNT_CUSTOMERS_YOUNGER_THAN_20,
                    Integer.class
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Customer> getYoungCustomers() {
        try {
            return jdbcTemplate.query(
                    BaseSqlRequest.GET_YOUNG_CUSTOMERS,
                    new BeanPropertyRowMapper<>(Customer.class)
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Magazine> get100MagazinesWithLimit() {
        return jdbcTemplate.query(
                BaseSqlRequest.GET_100_MAGAZINES_WITH_LIMIT,
                new BeanPropertyRowMapper<>(Magazine.class)
        );
    }

    public List<Magazine> get100MagazinesWithSorting() {
        return jdbcTemplate.query(
                BaseSqlRequest.GET_100_MAGAZINES_WITH_SORTING,
                new BeanPropertyRowMapper<>(Magazine.class)
        );
    }


}
