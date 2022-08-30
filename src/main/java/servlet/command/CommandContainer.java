package servlet.command;


import service.FilmService;
import service.ShowtimeService;
import service.TicketService;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that serves a container for <code>Controller</code> commands.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
public class CommandContainer {

    private final Map<String, Command> commands;





    public CommandContainer(TicketService ticketService, UserService userService, ShowtimeService showtimeService, FilmService filmService) {
        commands = new HashMap<>();
        commands.put("login", new LoginCommand(userService));
        commands.put("addFilm", new AddFilmCommand(filmService));
        commands.put("addShowtime", new AddShowtimeCommand(showtimeService,filmService));
        commands.put("register", new RegisterCommand(userService));
        commands.put("logout", new LogoutCommand());
        commands.put("cancelShowtime", new CancelShowtimeCommand(showtimeService));
        commands.put("purchaseTickets", new PurchaseTickets(ticketService,showtimeService));
        commands.put("printStats", new PrintStatsCommand(filmService,showtimeService));
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }

}
