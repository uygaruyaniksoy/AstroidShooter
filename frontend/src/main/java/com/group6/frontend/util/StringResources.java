package com.group6.frontend.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StringResources {

    public static String getEndGameText() {
        List<String> strings = Arrays.asList("Well... the game is over!",
                "You don't particularly play well,\n do you.",
                "Everything is over mate.",
                "Ok, you are done.");
        int i = new Random().nextInt(strings.size());
        return strings.get(i);
    }
}
