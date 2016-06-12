package com.example.jooq;

import com.example.jooq.db.tables.records.UsersRecord;
import com.example.model.QUser;
import com.example.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.Session;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

import static com.example.jooq.db.tables.Users.USERS;

public class UuidTest
{
    private static EntityManager em;

    private UUID userId;

    @BeforeClass
    public static void before()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("uuid-example-pu");

        em = emf.createEntityManager();
    }

    @Before
    public void insertNewUser()
    {
        em.unwrap(Session.class).doWork(connection ->
        {
            DSLContext create = DSL.using(connection);

            userId = UUID.randomUUID();

            create
                    .insertInto(USERS, USERS.ID, USERS.NAME)
                    .values(userId, userId.toString())
                    .execute();
        });
    }

    @Test
    public void uuid_query_should_not_fail_using_plain_jooq()
    {
        em.unwrap(Session.class).doWork(connection ->
        {
            DSLContext create = DSL.using(connection);

            UsersRecord record = create.selectFrom(USERS)
                    .where(USERS.ID.eq(userId))
                    .fetchOne();

            Assert.assertNotNull(record);
        });
    }

    @Test
    public void uuid_query_should_not_fail_using_hibernate_native_query()
    {
        Query nativeQuery = em.createNativeQuery("SELECT * FROM USERS u WHERE u.id = :userId");

        nativeQuery.setParameter("userId", userId);

        List<?> list = nativeQuery.getResultList();

        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void uuid_query_should_not_fail_using_querydsl()
    {
        User user = new JPAQueryFactory(em)
                .selectFrom(QUser.user)
                .where(QUser.user.id.eq(userId))
                .fetchOne();

        Assert.assertNotNull(user);
    }
}
