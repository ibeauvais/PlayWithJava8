package fr.xebia.xke.java8.step3;

import fr.xebia.xke.java8.data.Address;
import fr.xebia.xke.java8.data.Role;
import fr.xebia.xke.java8.data.User;
import fr.xebia.xke.java8.other.UserParser;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserService {

    private static final String DEFAULT_FORMATED_ADDRESS = "1 rue de Rivoli\n75001 Paris";
    private List<User> users;

    public UserService() {
        users = UserParser.fromCsv("users.csv");

    }

    public UserService(List<User> users) {
        this.users = users;
    }

    public long countUserWithRole(Role role) {
        //TODO: use filter and count
        return users.stream().
                filter(user -> user.getRole() == role).
                count();
    }

    public boolean isLoginAlreadyExist(String login) {
        //TODO: use anyMatch
        return users.stream().anyMatch(user -> user.getLogin().equals(login));
    }

    public String retrieveFormatedUserAddressByLogin(String login) {
        //TODO: user filter and findFirst. Replace user.address type by Optional
        return users.stream().
                filter(user -> user.getLogin().equals(login)).
                findFirst().
                flatMap(User::getAddress).
                map(Address::formatForEnveloppe)
                .orElse(DEFAULT_FORMATED_ADDRESS);

    }

    /**
     * Return a copy of users list ordered by lastname and firstname
     *
     * @return
     */
    public List<User> findAll() {
        //TODO: use sorted without specific comparator class creation. Use Comparator methods and Collectors
        return users.stream().
                sorted(
                        Comparator.comparing(User::getLastname).
                                thenComparing(User::getFirstname)
                ).
                collect(Collectors.toList());
    }

    public Map<Role, List<User>> retrieveActiveUserByRole() {
        //TODO: Use Collectors.groupingBy
        return users.stream().
                filter(user -> !user.isExpired()).
                collect(Collectors.groupingBy(User::getRole));
    }

    public Map<String, User> retrieveUserwithRoleByLogin(Role role) {
        //TODO: Use Collectors.toMap
        return users.stream().
                filter(user -> user.getRole() == role)
                .collect(Collectors.toMap(User::getLogin, Function.identity()));
    }

    public String generateAgeStatistic() {
        //TODO: use Collectors.summarizingInt
        IntSummaryStatistics summaryStatistics = users.stream().collect(Collectors.summarizingInt(User::age));

        return String.format("Number of user : %d\nAge min : %d\nAge max : %d\nAge average : %.2f", summaryStatistics.getCount(), summaryStatistics.getMin(), summaryStatistics.getMax(), summaryStatistics.getAverage());
    }
}