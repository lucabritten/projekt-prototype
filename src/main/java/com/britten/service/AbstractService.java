package com.britten.service;

import java.util.Optional;


/**
 * Abstrakte Service Klasse, definiert den Ablauf,
 * wie Daten für die Analyse gefetched werden,
 * konkrete Service Klassen implementieren die abstrakten methoden
 */
public abstract class AbstractService<T,K> {

    public T get(K key){
        return findInDb(key)
                .orElseGet(() -> {
                   T entity = fetchFromApi(key);
                   save(entity);
                   return entity;
                });
    }

    protected abstract Optional<T> findInDb(K key);
    protected abstract T fetchFromApi(K key);
    protected abstract void save(T entity);
}
