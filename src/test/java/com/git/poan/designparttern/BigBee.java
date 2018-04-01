package com.git.poan.designparttern;


class WomenSoundCard implements SoundCard {
    @Override
    public void speak() {
        System.out.println("婴婴婴～");

    }
}

interface SoundCard {
    void speak();
}


public class BigBee implements SoundCard{

    private SoundCard soundCard = new WomenSoundCard();


    @Override
    public void speak() {
        soundCard.speak();
    }

    public static void main(String[] args) {
        BigBee bigBee = new BigBee();
        bigBee.speak();
    }
}

