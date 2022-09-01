package com.epam.servlet.command;


import com.epam.annotation.processor.ServiceInjectionProcessor;
import com.epam.service.FilmService;
import com.epam.service.ShowtimeService;
import com.epam.service.TicketService;
import com.epam.service.UserService;

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





    public CommandContainer(ServiceInjectionProcessor serviceInjectionProcessor) {

        commands = new HashMap<>();
        commands.put("login", serviceInjectionProcessor.yakiysMethod2(LoginCommand.class));
        commands.put("addFilm", serviceInjectionProcessor.yakiysMethod2(AddFilmCommand.class));
        commands.put("addShowtime", serviceInjectionProcessor.yakiysMethod2(AddShowtimeCommand.class));
        commands.put("register", serviceInjectionProcessor.yakiysMethod2(RegisterCommand.class));
        commands.put("cancelShowtime", serviceInjectionProcessor.yakiysMethod2(CancelShowtimeCommand.class));
        commands.put("purchaseTickets", serviceInjectionProcessor.yakiysMethod2(PurchaseTicketsCommand.class));
        commands.put("printStats", serviceInjectionProcessor.yakiysMethod2(PrintStatsCommand.class));
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }

}
