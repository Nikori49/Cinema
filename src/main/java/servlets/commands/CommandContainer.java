package servlets.commands;


import java.util.HashMap;
import java.util.Map;

public class CommandContainer {


    private static final Map<String, Command> commands;

    static {
        commands = new HashMap<>();

        commands.put("login", new LoginCommand());
        commands.put("addFilm",new AddFilmCommand());
        commands.put("addShowtime",new AddShowtimeCommand());
        commands.put("register",new RegisterCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("cancelShowtime",new CancelShowtimeCommand());
        commands.put("purchaseTickets",new PurchaseTickets());
        commands.put("printStats",new PrintStatsCommand());



        // ...
    }

    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }

}
