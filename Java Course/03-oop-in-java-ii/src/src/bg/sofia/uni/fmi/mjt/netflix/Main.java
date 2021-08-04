package com.jetbrains;

import bg.sofia.uni.fmi.mjt.netflix.account.Account;
import bg.sofia.uni.fmi.mjt.netflix.content.Movie;
import bg.sofia.uni.fmi.mjt.netflix.content.Streamable;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.Genre;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.ContentUnavailableException;
import bg.sofia.uni.fmi.mjt.netflix.platform.Netflix;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws ContentUnavailableException {
        //creating an instance of Netflix platform
        LocalDateTime time1 = LocalDateTime.now();
        LocalDateTime time2 = LocalDateTime.of(1999, 10, 31, 16, 15, 20);

        Account acc1 = new Account("Us2", time2);
        Account acc2 = new Account("Us3", time1);
        Account[] accounts = {acc1, acc2};

        Movie mv1 = new Movie("mv1", Genre.HORROR, PgRating.G, 12);
        Movie mv2 = new Movie("mv2", Genre.ACTION, PgRating.NC17, 20);
        Movie mv3 = new Movie("mv3", Genre.ACTION, PgRating.NC17, 40);
        Streamable[] streamables = {mv1, mv2, mv3};

        Netflix netflix = new Netflix(accounts, streamables);

        //showing some functionality of the platform
        netflix.watch(acc1, "mv1");
        netflix.watch(acc1, "mv2");
        netflix.watch(acc1, "mv2");

        int watchedTime = netflix.totalWatchedTimeByUsers();
        System.out.println(watchedTime);
    }
}
