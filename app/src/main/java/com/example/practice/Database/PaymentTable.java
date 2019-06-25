package com.example.practice.Database;

public class PaymentTable {
    public static final String TABLE = "payments";

    public static class COLUMN {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String PAY = "pay";
    }

    public static final String CREATE_SCRIPT =
            String.format("create table %s ("
                            + "%s integer primary key autoincrement,"
                            + "%s text,"
                            + "%s text" + ");",
                    TABLE, COLUMN.ID, COLUMN.NAME, COLUMN.PAY);
}
