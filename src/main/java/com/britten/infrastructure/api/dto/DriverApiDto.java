package com.britten.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Api-Objekt modellierung (Felder müssen genauso heißen, wie sie in der json heißen, damit jackson das mappen kann)
 * Die Annotation bedeutet einfach, dass Felder die in der api vorkommen (hier zb. headshot_url)
 * die wir nicht für unser Projekt brauchen, einfach ignoriert werden, ohne die Annotation
 * fliegen exceptions
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DriverApiDto(
                           int driver_number,
                           String country_code,
                           String full_name) { }
