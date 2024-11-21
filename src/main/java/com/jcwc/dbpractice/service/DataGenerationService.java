package com.jcwc.dbpractice.service;

import com.jcwc.dbpractice.domain.repository.jpa.*;
import com.jcwc.dbpractice.entity.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DataGenerationService {

    private final AuthorRepository authorRepository;
    private final CustomerRepository customerRepository;
    private final DepartmentRepository departmentRepository;
    private final InventoryRepository inventoryRepository;
    private final MagazineRepository magazineRepository;
    private final OrderRepository orderRepository;
    private final PublisherRepository publisherRepository;
    private final SeriesRepository seriesRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void generatePublishers(Faker faker, int count) {
        List<Publisher> publishers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Publisher publisher = new Publisher();
            publisher.setName(faker.book().publisher());
            publishers.add(publisher);
        }
        publisherRepository.saveAll(publishers);
    }

    @Transactional
    public void generateSeries(Faker faker, int count) {
        List<Series> seriesList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Series series = new Series();
            series.setName(faker.book().genre());
            seriesList.add(series);
        }
        seriesRepository.saveAll(seriesList);
    }

    @Transactional
    public void generateAuthors(Faker faker, int count) {
        List<Author> authors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Author author = new Author();
            author.setName(faker.book().author());
            authors.add(author);
        }
        authorRepository.saveAll(authors);
    }

    @Transactional
    public void generateMagazines(Faker faker, int count) {
        List<Series> seriesList = seriesRepository.findAll();
        List<Publisher> publishers = publisherRepository.findAll();
        List<Author> authors = authorRepository.findAll();

        List<Magazine> magazines = new ArrayList<>();
        int batchSize = 1000;

        for (int i = 1; i <= count; i++) {
            Magazine magazine = new Magazine();
            magazine.setTitle(faker.book().title());
            magazine.setIsbn(String.valueOf(i));
            magazine.setSeries(seriesList.get(faker.number().numberBetween(0, seriesList.size())));
            magazine.setPublisher(publishers.get(faker.number().numberBetween(0, publishers.size())));
            magazine.setPublicationYear(faker.number().numberBetween(1900, 2024));
            magazine.setPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 100)));
            Author author = authors.get(faker.random().nextInt(authors.size()));
            MagazineAuthor magazineAuthor = new MagazineAuthor();
            magazineAuthor.setMagazine(magazine);
            magazineAuthor.setAuthor(author);
            magazine.getMagazineAuthors().add(magazineAuthor);
            magazines.add(magazine);

            if (i % batchSize == 0) {
                magazineRepository.saveAll(magazines);
                magazineRepository.flush();
                magazines.clear();
            }
        }
        if (!magazines.isEmpty()) {
            magazineRepository.saveAll(magazines);
            magazineRepository.flush();
            magazines.clear();
        }
    }


    @Transactional
    public void generateStores(Faker faker, int count) {
        List<Store> stores = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Store store = new Store();
            store.setName(faker.company().name());
            store.setAddress(faker.address().fullAddress());
            stores.add(store);
        }
        storeRepository.saveAll(stores);
    }

    @Transactional
    public void generateDepartments(Faker faker) {
        List<Store> stores = storeRepository.findAll();
        List<Department> departments = new ArrayList<>();
        for (Store store : stores) {
            Department department = new Department();
            department.setStore(store);
            department.setNumber(1);
            department.setName(faker.commerce().department());
            department.setCapacity(1000);
            departments.add(department);
        }
        departmentRepository.saveAll(departments);
    }

    @Transactional
    public void generateInventories(Faker faker) {
        List<Department> departments = departmentRepository.findAll();
        List<Magazine> magazines = magazineRepository.findAll();

        List<Inventory> inventories = new ArrayList<>();
        int batchSize = 1000;
        int i = 0;
        for (Department department : departments) {
            int magazineIndex = faker.number().numberBetween(0, magazines.size());
            Magazine magazine = magazines.get(magazineIndex);
            Inventory inventory = new Inventory();
            inventory.setDepartment(department);
            inventory.setMagazine(magazine);
            inventory.setQuantity(faker.number().numberBetween(1, 101));
            inventories.add(inventory);
            i++;
            if (i % batchSize == 0) {
                inventoryRepository.saveAll(inventories);
                inventoryRepository.flush();
                inventories.clear();
            }

        }
        if (!inventories.isEmpty()) {
            inventoryRepository.saveAll(inventories);
            inventoryRepository.flush();
            inventories.clear();
        }
    }

    @Transactional
    public void generateCustomers(Faker faker, int count) {
        List<Customer> customers = new ArrayList<>();
        int batchSize = 1000;

        for (int i = 1; i <= count; i++) {
            Customer customer = new Customer();
            customer.setFullName(faker.name().fullName());
            customer.setEmail(i + faker.internet().emailAddress());
            customer.setBirthDate(faker.timeAndDate().birthday(18, 80));
            customer.setAddress(faker.address().fullAddress());
            customer.setPhoneNumber(faker.phoneNumber().phoneNumber());

            customers.add(customer);

            if (i % batchSize == 0) {
                customerRepository.saveAll(customers);
                customerRepository.flush();
                customers.clear();
            }
        }
        if (!customers.isEmpty()) {
            customerRepository.saveAll(customers);
            customerRepository.flush();
            customers.clear();
        }
    }

    @Transactional
    public void generateOrders(Faker faker) {
        List<Customer> customers = customerRepository.findAll();
        List<Magazine> magazines = magazineRepository.findAll();
        List<Store> stores = storeRepository.findAll();

        List<Order> orders = new ArrayList<>();
        int batchSize = 1000;
        int orderCount = 0;

        for (Customer customer : customers) {
            Order order = new Order();
            order.setCustomer(customer);
            order.setStore(stores.get(faker.number().numberBetween(0, stores.size())));
            order.setOrderDate(faker.timeAndDate().past(365, TimeUnit.DAYS).atZone(ZoneId.systemDefault()).toLocalDate());
            order.setStatus(faker.options().option(Status.class));

            List<OrderItem> orderItems = new ArrayList<>();
            int magazineIndex = faker.number().numberBetween(0, magazines.size());
            Magazine magazine = magazines.get(magazineIndex);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMagazine(magazine);
            orderItem.setQuantity(faker.number().numberBetween(1, 6));

            orderItems.add(orderItem);
            order.setOrderItems(orderItems);
            orders.add(order);

            orderCount++;
            if (orderCount % batchSize == 0) {
                orderRepository.saveAll(orders);
                orderRepository.flush();
                orders.clear();
            }

        }
        if (!orders.isEmpty()) {
            orderRepository.saveAll(orders);
            orderRepository.flush();
            orders.clear();
        }
    }
}
