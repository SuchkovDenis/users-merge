package ru.suchkov.merger;

import java.util.*;

public class MergeUtil {

    private MergeUtil() {
    }

    /**
     * Returns merged map of users with their emails without duplicates
     *
     * @param input map of users->{emails} with duplicates
     * @return merged map of users
     */
    public static Map<String, Set<String>> merge(Map<String, Set<String>> input) {
        return collect(decompose(input));
    }

    /**
     * Creates map of emails->users
     *
     * @param input map of users->{emails} with duplicates
     * @return map of emails->users
     */
    private static Map<String, String> decompose(Map<String, Set<String>> input) {
        Map<String, String> emailUserMap = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry : input.entrySet()) {
            if (emailUserMap.entrySet().isEmpty()) { // первая запись
                for (String email : entry.getValue()) {
                    emailUserMap.put(email, entry.getKey());
                }
            } else { // все остальные записи
                String user = entry.getKey();
                for (String email : entry.getValue()) {
                    if (emailUserMap.containsKey(email)) {
                        user = emailUserMap.get(email);
                        break;
                    }
                }
                for (String email : entry.getValue()) {
                    emailUserMap.put(email, user);
                }
            }
        }

        return emailUserMap;
    }

    /**
     * Collects map of users->{emails} without duplicates from map of emails->users without duplicates
     *
     * @param emailUserMap  map of emails->users
     * @return map of users->{emails} without duplicates
     */
    private static Map<String, Set<String>> collect(Map<String, String> emailUserMap) {
        Map<String, Set<String>> out = new HashMap<>();

        for (Map.Entry<String, String> entry : emailUserMap.entrySet()) {
            String user = entry.getValue();
            String email = entry.getKey();

            if (out.containsKey(user)) {
                out.get(user).add(email);
            } else {
                out.put(user, new HashSet<>(Collections.singletonList(email)));
            }
        }

        return out;
    }
}
