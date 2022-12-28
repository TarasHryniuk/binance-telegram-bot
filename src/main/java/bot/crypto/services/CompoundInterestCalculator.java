package bot.crypto.services;

import java.time.LocalDate;

/**
 * @author tarashryniuk
 * @created on 12/28/2022 - 10:23
 * @email: hryniuk.t@gmail.com
 * @project: crypto-currencies-telegram-bot
 */
public class CompoundInterestCalculator {

    public static void result(Double startAmount, Double endAmount, Double percent) {
        Integer days = 0;

        while (endAmount >= startAmount) {

            startAmount += startAmount / 100 * percent / 365;

            System.out.println(startAmount);

            days = days + 1;

            System.out.println(days);
        }

        LocalDate ld = LocalDate.now();

        System.out.println(ld.plusDays(days));

    }


    public static void main(String[] args) {
        result(5258D, 7002D, 15.66D);
    }

}
