package com.watheq.watheq.cache;

import com.watheq.watheq.model.LoginModelResponse;

import io.paperdb.Book;
import io.paperdb.Paper;

/**
 * Created by mohamed.ibrahim on 6/11/2017.
 */

public class UserCache {
    private static final String Map_NAME = "_user";
    private static final String USER_KEY = "_user_key";

    private final Book book;

    public UserCache() {
        book = Paper.book(Map_NAME);
    }


    public LoginModelResponse getUser() {
        return book.read(USER_KEY, null);
    }

    public void save(LoginModelResponse user) {
        book.write(USER_KEY, user);
    }


    public void deleteUser() {
        book.delete(USER_KEY);
    }
}
