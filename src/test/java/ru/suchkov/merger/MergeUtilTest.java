package ru.suchkov.merger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MergeUtilTest {

    @Parameterized.Parameters
    public static Collection<Object[]>  data(){

        Map<String, Set<String>> input1 = new HashMap<>();
        input1.put("user1", new HashSet<>(Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru")));
        input1.put("user2", new HashSet<>(Arrays.asList("foo@gmail.com", "ups@pisem.net")));
        input1.put("user3", new HashSet<>(Arrays.asList("xyz@pisem.net", "vasya@pupkin.com")));
        input1.put("user4", new HashSet<>(Arrays.asList("ups@pisem.net", "aaa@bbb.ru")));
        input1.put("user5", new HashSet<>(Collections.singletonList("xyz@pisem.net")));
        Set<Set<String>> emailValues1 = new HashSet<>(Arrays.asList(
                new HashSet<>(Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru", "ups@pisem.net", "aaa@bbb.ru")),
                new HashSet<>(Arrays.asList("xyz@pisem.net", "vasya@pupkin.com"))
        ));
        int differentAccounts1 = 2;

        Map<String, Set<String>> input2 = new HashMap<>();
        input2.put("user1", new HashSet<>(Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru")));
        Set<Set<String>> emailValues2 = new HashSet<>(Collections.singletonList(
                new HashSet<>(Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru"))
        ));
        int differentAccounts2 = 1;

        Map<String, Set<String>> input3 = new HashMap<>();
        input3.put("user1", new HashSet<>(Arrays.asList("xxx1@ya.ru", "foo1@gmail.com", "lol1@mail.ru")));
        input3.put("user2", new HashSet<>(Arrays.asList("foo2@gmail.com", "ups2@pisem.net")));
        input3.put("user3", new HashSet<>(Arrays.asList("xyz3@pisem.net", "vasya3@pupkin.com")));
        input3.put("user4", new HashSet<>(Arrays.asList("ups4@pisem.net", "aaa4@bbb.ru")));
        input3.put("user5", new HashSet<>(Collections.singletonList("xyz5@pisem.net")));
        Set<Set<String>> emailValues3 = new HashSet<>(Arrays.asList(
                new HashSet<>(Arrays.asList("xxx1@ya.ru", "foo1@gmail.com", "lol1@mail.ru")),
                new HashSet<>(Arrays.asList("foo2@gmail.com", "ups2@pisem.net")),
                new HashSet<>(Arrays.asList("xyz3@pisem.net", "vasya3@pupkin.com")),
                new HashSet<>(Arrays.asList("ups4@pisem.net", "aaa4@bbb.ru")),
                new HashSet<>(Collections.singletonList("xyz5@pisem.net"))
        ));
        int differentAccounts3 = 5;

        Map<String, Set<String>> input4 = new HashMap<>();
        input4.put("user1", new HashSet<>(Arrays.asList("a@ya.ru", "b@ya.ru", "c@ya.ru")));
        input4.put("user2", new HashSet<>(Arrays.asList("d@ya", "a@ya.ru", "b@ya.ru")));
        Set<Set<String>> emailValues4 = new HashSet<>(Collections.singletonList(
                new HashSet<>(Arrays.asList("a@ya.ru", "b@ya.ru", "c@ya.ru", "d@ya"))
        ));
        int differentAccounts4 = 1;


        return Arrays.asList(new Object[][] {
                {input1, differentAccounts1, emailValues1},
                {input2, differentAccounts2, emailValues2},
                {input3, differentAccounts3, emailValues3},
                {input4, differentAccounts4, emailValues4}
        });
    }

    private Map<String, Set<String>> input;
    private Set<Set<String>> emailValues;
    private int differentUsers;

    public MergeUtilTest(Map<String, Set<String>> input, int differentUsers, Set<Set<String>> emailValues) {
        this.input = input;
        this.emailValues = emailValues;
        this.differentUsers = differentUsers;
    }

    /**
     * Check number of unique users
     */
    @Test
    public void checkNumberOfUsers() {
        assertEquals(differentUsers, MergeUtil.merge(input).entrySet().size());
    }

    /**
     * Check set of emails
     */
    @Test
    public void checkMails() {
        Set<Set<String>> actual = new HashSet<>(MergeUtil.merge(input).values());
        assertEquals(emailValues, actual);
    }
}