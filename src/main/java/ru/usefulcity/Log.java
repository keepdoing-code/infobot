package ru.usefulcity;

import java.time.LocalDateTime;

public class Log {
    public static void out(String... args) {

        StringBuilder sb = new StringBuilder(LocalDateTime.now().toString());
        for (String str : args) {
            sb.append(" ").append(str);
        }
        sb.append('\n');
        System.out.printf(sb.toString());

    }
}
