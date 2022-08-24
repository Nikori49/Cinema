package DB;

import DB.entity.Film;
import DB.entity.Showtime;
import DB.entity.Ticket;
import DB.entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class DBManager {
    private static DBManager instance;
    private final Connection connection;


    public static synchronized DBManager getInstance() {
        if (instance == null) {
            try {
                instance = new DBManager();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private DBManager() throws SQLException {
        this.connection = ConnectionPool.getInstance().getConnection();
    }

    public static final String GET_USER_BY_ROLE="SELECT * FROM users where role=?";
    public static final String GET_USER_BY_LOGIN="SELECT * FROM users WHERE login=?";
    public static final String GET_USER_BY_EMAIL="SELECT * FROM users WHERE email=?";
    public static final String GET_USER_BY_PHONE_NUMBER="SELECT * FROM users WHERE phone_number=?";
    public static final String GET_ALL_USERS="SELECT * FROM users";
    public static final String INSERT_USER="INSERT INTO users VALUES (DEFAULT,?,?,?,?,?,md5(?),'client')";

    public User getUser(String searchParameter, String statement) {
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(statement)){
            preparedStatement.setString(1, searchParameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = extractUser(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(extractUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public User findUserByEMail(String email) {
        return getUser(email, GET_USER_BY_EMAIL);
    }

    public User findUserByRole(String role) {
        return getUser(role, GET_USER_BY_ROLE);
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        return getUser(phoneNumber, GET_USER_BY_PHONE_NUMBER);
    }

    public User findUserByLogin(String login) {
        return getUser(login, GET_USER_BY_LOGIN);
    }

    public User insertUser(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getLogin());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return user;
    }

    private User extractUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(resultSet.getString("role"));
        return user;
    }

    public void createShowTime(Showtime showtime)  {
        try {
            PreparedStatement preparedStatementInsertShowTime = connection.prepareStatement("INSERT INTO show_times VALUES (DEFAULT,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatementInsertShowTime.setLong(1,showtime.getFilmId());
            preparedStatementInsertShowTime.setDate(2,showtime.getDate());
            preparedStatementInsertShowTime.setString(3,showtime.getStatus());
            preparedStatementInsertShowTime.setTime(4,showtime.getStartTime());
            preparedStatementInsertShowTime.setTime(5,showtime.getEndTime());
            preparedStatementInsertShowTime.setLong(6,showtime.getId());
            preparedStatementInsertShowTime.execute();
            ResultSet resultSet = preparedStatementInsertShowTime.getGeneratedKeys();
            while (resultSet.next()) {
                showtime.setId(resultSet.getLong(1));
            }
            PreparedStatement preparedStatementUpdateSeatTableId = connection.prepareStatement("UPDATE show_times SET seatsTableId=? where id=?");
            preparedStatementUpdateSeatTableId.setLong(1,showtime.getId());
            preparedStatementUpdateSeatTableId.setLong(2,showtime.getId());
            preparedStatementUpdateSeatTableId.execute();
            String tableName="show_time_seats_"+showtime.getId();
            System.out.println(tableName);
            PreparedStatement preparedStatementCreateSeatTable = connection.prepareStatement("CREATE TABLE "+tableName+
                    "(   id     BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "    seat   VARCHAR(10) not null," +
                    "    status VARCHAR(20)" +
                    ")");
            preparedStatementCreateSeatTable.execute();
            TreeMap <String,String> seatMap = Utils.fillSeatMap();
            seatMap.descendingKeySet();
            for (String seat:seatMap.descendingKeySet()) {
                PreparedStatement preparedStatementFillSeatTable = connection.prepareStatement("INSERT INTO "+tableName+" VALUES (DEFAULT,?,?)");
                preparedStatementFillSeatTable.setString(1,seat);
                preparedStatementFillSeatTable.setString(2,seatMap.get(seat));
                preparedStatementFillSeatTable.execute();
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Showtime> getShowtimeForDate(Date date){
        List <Showtime> showtimeList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM show_times WHERE date=?");
            preparedStatement.setDate(1,date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               Showtime showtime = extractShowtime(resultSet);

               showtimeList.add(showtime);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }



        return showtimeList;
    }

    public List<Showtime> getShowtimeForFilm(Long filmId){
        List <Showtime> showtimeList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM show_times WHERE filmId=?");
            preparedStatement.setLong(1,filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Showtime showtime = extractShowtime(resultSet);

                showtimeList.add(showtime);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }



        return showtimeList;
    }



    public Showtime getShowTime(Long id){
        Showtime showtime = null;
        try {
          PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM show_times WHERE id=?");
          preparedStatement.setLong(1,id);
          ResultSet resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
            showtime = extractShowtime(resultSet);
          }


      }catch (SQLException e){
          e.printStackTrace();
      }
        return showtime;
    }

    public void cancelShowtime(Long id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cinema.show_times SET status='canceled' WHERE cinema.show_times.id=?");
            preparedStatement.setLong(1,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertFilm(Film film){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO films values (DEFAULT,?,?,?,?,?,?)");
            preparedStatement.setString(1,film.getName());
            preparedStatement.setString(2,film.getDescription());
            preparedStatement.setString(3,film.getGenre());
            preparedStatement.setString(4,film.getPosterImgPath());
            preparedStatement.setString(5,film.getDirector());
            preparedStatement.setLong(6,film.getRunningTime());
            preparedStatement.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Film getFilm(Long id){
        Film film = new Film();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM films WHERE id=?");
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                film=extractFilm(resultSet);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return film;
    }

    public List<Film> getAllFilms(){
        List<Film> filmList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM films");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                filmList.add(extractFilm(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return filmList;
    }

    public Film extractFilm(ResultSet resultSet) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getLong("id"));
        film.setName(resultSet.getString("name"));
        film.setDescription(resultSet.getString("description"));
        film.setGenre(resultSet.getString("genre"));
        film.setPosterImgPath(resultSet.getString("posterImgPath"));
        film.setDirector(resultSet.getString("director"));
        film.setRunningTime(resultSet.getLong("runningTime"));
        return film;
    }

    public Showtime extractShowtime(ResultSet resultSet) throws SQLException {
        Showtime showtime = new Showtime();
        showtime.setId(resultSet.getLong("id"));
        showtime.setFilmId(resultSet.getLong("filmId"));
        showtime.setDate(resultSet.getDate("date"));
        showtime.setStatus(resultSet.getString("status"));
        showtime.setStartTime(resultSet.getTime("startTime"));
        showtime.setEndTime(resultSet.getTime("endTime"));
        String tableName = "show_time_seats_"+resultSet.getLong("seatsTableId");
        PreparedStatement preparedStatementGetSeatTable = connection.prepareStatement("SELECT * FROM "+tableName);
        ResultSet resultSetSeatTable = preparedStatementGetSeatTable.executeQuery();
        TreeMap<String,String> seatMap = new TreeMap<>();
        while (resultSetSeatTable.next()){

            seatMap.put(resultSetSeatTable.getString("seat"),resultSetSeatTable.getString("status"));
        }
        showtime.setSeats(seatMap);
        return showtime;
    }

    public void insertTicket(Ticket ticket){
        try {
            PreparedStatement preparedStatementForTicket = connection.prepareStatement("INSERT INTO tickets VALUES (DEFAULT,?,?,?)");
            preparedStatementForTicket.setLong(1,ticket.getUserId());
            preparedStatementForTicket.setLong(2,ticket.getShowTimeId());
            preparedStatementForTicket.setString(3,ticket.getSeat());
            preparedStatementForTicket.execute();
            String tableName = "show_time_seats_"+ticket.getShowTimeId();
            PreparedStatement preparedStatementForSeat = connection.prepareStatement("UPDATE "+tableName+" SET status='occupied' WHERE seat=?");
            preparedStatementForSeat.setString(1,ticket.getSeat());
            preparedStatementForSeat.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Ticket getTicket(Long id){
        Ticket ticket = new Ticket();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tickets WHERE id=?");
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                ticket=extractTicket(resultSet);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ticket;
    }

    public List<Showtime> getPlannedShowtimes(){
        List<Showtime> showtimeList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM show_times WHERE status='planned'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                showtimeList.add(extractShowtime(resultSet)) ;
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        if (showtimeList.isEmpty()){
            return null;
        }
        return showtimeList;
    }

    public String getSeatStatus(String key,Long id){
        String status = null;
        try {
            String tableName = "show_time_seats_"+id;
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE seat=?");
            preparedStatement.setString(1,key);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
               status = resultSet.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public List<Ticket> getUserTickets(User user){
        List <Ticket> ticketList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tickets WHERE userId=?");
            preparedStatement.setLong(1,user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ticketList.add(extractTicket(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ticketList;
    }

    public Ticket extractTicket(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getLong("id"));
        ticket.setUserId(resultSet.getLong("userId"));
        ticket.setShowTimeId(resultSet.getLong("showTimeId"));
        ticket.setSeat(resultSet.getString("seat"));
        return ticket;
    }

    public  List<Showtime> getShowtimesForMonth(Date firstDay){
        List<Showtime> showtimeList = new ArrayList<>();
        Date lastDay = Date.valueOf(firstDay.toLocalDate().plusMonths(1));
        try {PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM show_times WHERE date>=? AND date<=?");
            preparedStatement.setDate(1,firstDay);
            preparedStatement.setDate(2,lastDay);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                showtimeList.add(extractShowtime(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtimeList;
    }

    public  List<List<Showtime>> getShowtimesForWeek(){
        List<List<Showtime>> weekList = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        Date date =  Date.valueOf(localDate);
        System.out.println(date);
        long dateLong = date.getTime();
        for (int i=0;i<7;i++){
            try {
                List<Showtime> showtimeList = new ArrayList<>();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM show_times WHERE date=? AND status='planned'");
                preparedStatement.setDate(1,new Date(dateLong));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    showtimeList.add(extractShowtime(resultSet));
                }
                showtimeList.sort((o1, o2) -> (int) (o1.getStartTime().getTime()-o2.getStartTime().getTime()));
                weekList.add(showtimeList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dateLong = dateLong+86_400_000;
        }
        return  weekList;
    }



}
