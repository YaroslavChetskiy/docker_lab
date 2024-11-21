package com.jcwc.dbpractice.domain.commandLineRunner.appRunner;

import com.jcwc.dbpractice.domain.repository.BasicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@Order(3)
@RequiredArgsConstructor
public class BenchmarkRunner implements CommandLineRunner {

    private final BasicRepository basicRepository;

    @Override
    public void run(String... args) {
        System.out.println("\n\n");
        System.out.println("================");

        // Измеряем время выполнения каждого запроса
        measureExecutionTime("Какие журналы были куплены определенным покупателем?", () -> {
            basicRepository.getMagazinesByCustomerId(4L);
        });

        measureExecutionTime("Как называется журнал с заданным ISBN?", () -> {
            basicRepository.getMagazineTitleByIsbn("978-1-933321-38-4");
        });

        measureExecutionTime("Какой ISBN у журнала с заданным названием?", () -> {
            basicRepository.getIsbnByMagazineTitle("Ведьма и зверь. Том 1");
        });

        measureExecutionTime("Когда журнал был куплен?", () -> {
            basicRepository.getOrderDateByMagazineTitle("Бесобой. Том 1: Имя ему Бесобой");
        });

        measureExecutionTime("Кто из покупателей купил журнал более месяца тому назад?",
                basicRepository::getCustomersWithOrdersOlderThanOneMonth);

        measureExecutionTime("Если с датой:", basicRepository::getCustomersWithOrdersOlderThanOneMonthWithDate);

        measureExecutionTime("Найти покупателя самых редких журналов (по наличию в магазине)",
                basicRepository::getCustomersOfRarestMagazines);

        measureExecutionTime("Какое число покупателей пользуется определенным магазином?",
                basicRepository::getCustomerCountsByStore);

        measureExecutionTime("Сколько покупателей младше 20 лет?",
                basicRepository::countCustomersYoungerThan20);

        System.out.println("================");
        System.out.println("\n\n");
    }

    private void measureExecutionTime(String description, Runnable query) {
        long startTime = System.currentTimeMillis();
        query.run();
        long endTime = System.currentTimeMillis();
        System.out.println(description + " выполнено за " + (endTime - startTime) + " мс.");
        System.out.println("\n");
    }
}
