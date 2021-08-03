package com.jetbrains;

import bg.sofia.uni.fmi.mjt.revolut.Revolut;
import bg.sofia.uni.fmi.mjt.revolut.account.Account;
import bg.sofia.uni.fmi.mjt.revolut.account.BGNAccount;
import bg.sofia.uni.fmi.mjt.revolut.account.EURAccount;
import bg.sofia.uni.fmi.mjt.revolut.card.Card;
import bg.sofia.uni.fmi.mjt.revolut.card.PhysicalCard;
import bg.sofia.uni.fmi.mjt.revolut.card.VirtualOneTimeCard;
import bg.sofia.uni.fmi.mjt.revolut.card.VirtualPermanentCard;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        //creating revolut with different accounts and cards

        LocalDate ld = LocalDate.of(2021, 6, 30);

        Card pc1 = new PhysicalCard("abc", 1234, ld);
        Card pc2 = new PhysicalCard("def", 1234, ld);
        Card vpc = new VirtualPermanentCard("mnk", 1234, ld);
        Card votp = new VirtualOneTimeCard("lpr", 1234, ld);
        Card[] cards = new Card[]{pc1, pc2, vpc, votp};

        Account bga1 = new BGNAccount("IBAN1",50);
        Account bga2 = new BGNAccount("IBAN2",100);
        Account eua1 = new EURAccount("IBAN3",150);
        Account[] accounts = new Account[]{bga1, bga2, eua1};

        Revolut revolut = new Revolut(accounts, cards);

        //showing different functionalities of revolut
        /*
        System.out.println(revolut.getTotalAmount());
        System.out.println(revolut.payOnline(votp, 1234, 10, "BGN","shop.com"));
        System.out.println(revolut.getTotalAmount());
        System.out.println(revolut.payOnline(votp, 1234, 20, "BGN","shop.com"));
        System.out.println(revolut.getTotalAmount());
        System.out.println(revolut.pay(pc2, 1334, 120, "EUR"));
        System.out.println(revolut.getTotalAmount());
        System.out.println(revolut.pay(pc2, 1234, 20, "BGN"));
        System.out.println(revolut.getTotalAmount());
        System.out.println(revolut.pay(pc1, 1234, 30, "EUR"));
        System.out.println(revolut.getTotalAmount());
        System.out.println(revolut.pay(pc1, 1234, 10, "BGN"));
        System.out.println(revolut.getTotalAmount());
        System.out.println(revolut.addMoney(eua1, 50));
        System.out.println(revolut.getTotalAmount());
        System.out.println(revolut.transferMoney(eua1,bga1,300));
        System.out.println(revolut.getTotalAmount());
        System.out.println(revolut.payOnline(vpc, 1234, 10, "BGN", "shop.com"));
        System.out.println(revolut.getTotalAmount());
         */
    }
}
