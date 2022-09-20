package com.epam.servlet.command;


import com.epam.annotation.processor.ServiceInjectionProcessor;

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
        commands.put("login", serviceInjectionProcessor.createCommand(LoginCommand.class));
        commands.put("addFilm", serviceInjectionProcessor.createCommand(AddFilmCommand.class));
        commands.put("addShowtime", serviceInjectionProcessor.createCommand(AddShowtimeCommand.class));
        commands.put("register", serviceInjectionProcessor.createCommand(RegisterCommand.class));
        commands.put("cancelShowtime", serviceInjectionProcessor.createCommand(CancelShowtimeCommand.class));
        commands.put("purchaseTickets", serviceInjectionProcessor.createCommand(PurchaseTicketsCommand.class));
        commands.put("printStats", serviceInjectionProcessor.createCommand(PrintStatsCommand.class));
        commands.put("addBalance",serviceInjectionProcessor.createCommand(AddBalanceCommand.class));
        commands.put("refund",serviceInjectionProcessor.createCommand(RefundTicketCommand.class));
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }

}
