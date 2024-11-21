package com.jcwc.dbpractice.domain.commandLineRunner.appRunner;

import com.jcwc.dbpractice.domain.repository.BasicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@Order(3)
@RequiredArgsConstructor
public class SortJoinBenchmarkRunner implements CommandLineRunner {

    private final BasicRepository basicRepository;

    @Override
    public void run(String... args) {
        System.out.println("\n\n");
        System.out.println("================");


        measureExecutionTime("Получить 100 журналов без сортировки",
                basicRepository::get100MagazinesWithLimit);

        measureExecutionTime("Получить 100 журналов с сортировкой по названию",
                basicRepository::get100MagazinesWithSorting);

        measureExecutionTime("Найти покупателя самых редких журналов (по наличию в магазине)",
                basicRepository::getCustomersOfRarestMagazines);

        System.out.println("================");
        System.out.println("\n\n");
    }

    private void measureExecutionTime(String description, Runnable query) {
        long startTime = System.currentTimeMillis();
        query.run();
        long endTime = System.currentTimeMillis();
        System.out.println(description + " выполнено за " + (endTime - startTime) + " мс.");
    }
}

