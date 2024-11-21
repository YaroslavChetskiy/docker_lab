package com.jcwc.dbpractice.domain.commandLineRunner.appRunner;

import com.jcwc.dbpractice.domain.repository.BasicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@Order(3)
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final BasicRepository basicRepository;

    @Override
    public void run(String... args) {
        System.out.println("\n\n");
        System.out.println("================");

        System.out.println("Какие журналы были куплены определенным покупателем?");
        basicRepository.getMagazinesByCustomerId(4L).forEach(System.out::println);
        System.out.println("\n");

        System.out.println("Как называется журнал с заданным ISBN?");
        System.out.println(basicRepository.getMagazineTitleByIsbn("9781933321384"));
        System.out.println("\n");

        System.out.println("Какой ISBN у журнала с заданным названием?");
        basicRepository.getIsbnByMagazineTitle("Ведьма и зверь. Том 1").forEach(System.out::println);
        System.out.println("\n");

        System.out.println("Когда журнал был куплен?");
        basicRepository.getOrderDateByMagazineTitle("Бесобой. Том 1: Имя ему Бесобой").forEach(System.out::println);
        System.out.println("\n");

        System.out.println("Кто из покупателей купил журнал более месяца тому назад?");
        basicRepository.getCustomersWithOrdersOlderThanOneMonth().forEach(System.out::println);
        System.out.println("\n");

        System.out.println("Если с датой:");
        basicRepository.getCustomersWithOrdersOlderThanOneMonthWithDate().forEach(System.out::println);
        System.out.println("\n");

        System.out.println("Найти покупателя самых редких журналов (по наличию в магазине)");
        basicRepository.getCustomersOfRarestMagazines().forEach(System.out::println);
        System.out.println("\n");

        System.out.println("Какое число покупателей пользуется определенным магазином?");
        basicRepository.getCustomerCountsByStore().forEach(System.out::println);
        System.out.println("\n");

        System.out.println("Сколько покупателей младше 20 лет?");
        System.out.println(basicRepository.countCustomersYoungerThan20());
        System.out.println("\n");

        System.out.println("Если нужны сами покупатели:");
        basicRepository.getYoungCustomers().forEach(System.out::println);
        System.out.println("\n");

        System.out.println("================");
        System.out.println("\n\n");
    }
}

