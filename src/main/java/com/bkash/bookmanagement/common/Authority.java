package com.bkash.bookmanagement.common;

public class Authority {
    public class Plan {
        public static final String ADMIN = "hasAuthority('ROLE_ADMIN')";
        public static final String WRITE = "hasAuthority('ROLE_WRITE')";
    }
}
