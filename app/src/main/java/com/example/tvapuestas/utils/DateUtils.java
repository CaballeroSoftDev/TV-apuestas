package com.example.tvapuestas.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String convertUtcToLocalDateTime(String utcDateTime) {

        Instant instant = Instant.parse(utcDateTime);

        // Obtener zona horaria local del dispositivo
        ZoneId localZone = ZoneId.systemDefault();

        // Convertir el Instant a LocalDateTime en la zona local
        LocalDateTime localDateTime = instant.atZone(localZone).toLocalDateTime();

        // Formatear la fecha como string "yyyy-MM-dd HH:mm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

        return localDateTime.format(formatter);
    }

}
