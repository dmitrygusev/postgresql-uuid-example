package com.example.hibernate;

import org.hibernate.dialect.PostgreSQL9Dialect;

import java.sql.Types;

public class CustomPostgreSQL9Dialect extends PostgreSQL9Dialect
{
    public CustomPostgreSQL9Dialect()
    {
        registerHibernateType(Types.OTHER, "pg-uuid");
    }
}