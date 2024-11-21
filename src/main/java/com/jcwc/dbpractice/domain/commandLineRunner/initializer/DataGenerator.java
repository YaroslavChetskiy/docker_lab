package com.jcwc.dbpractice.domain.commandLineRunner.initializer;

import com.jcwc.dbpractice.service.DataGenerationService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Locale;

//@Component
@RequiredArgsConstructor
@Order(2)
public class DataGenerator implements CommandLineRunner {

    private static final Integer COUNT = 100_000;
    private final DataGenerationService dataGenerationService;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Locale("ru"));

        System.out.println("Генерируем издателей");
        dataGenerationService.generatePublishers(faker, COUNT);
        System.out.println("Генерируем серии");
        dataGenerationService.generateSeries(faker, COUNT);
        System.out.println("Генерируем авторов");
        dataGenerationService.generateAuthors(faker, COUNT);
        System.out.println("Генерируем журналы");
        dataGenerationService.generateMagazines(faker, COUNT);
        System.out.println("Генерируем магазины");
        dataGenerationService.generateStores(faker, COUNT);
        System.out.println("Генерируем отделы");
        dataGenerationService.generateDepartments(faker);
        System.out.println("Генерируем инвентари");
        dataGenerationService.generateInventories(faker);
        System.out.println("Генерируем покупателей");
        dataGenerationService.generateCustomers(faker, COUNT);
        System.out.println("Генерируем заказы");
        dataGenerationService.generateOrders(faker);
    }
}
