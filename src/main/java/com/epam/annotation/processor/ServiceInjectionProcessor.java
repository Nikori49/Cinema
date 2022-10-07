package com.epam.annotation.processor;

import com.epam.annotation.MyInject;
import com.epam.annotation.Service;
import com.epam.dao.*;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.*;

public class ServiceInjectionProcessor {



    private final HashMap<Class<?>, Object> map = new HashMap();

    public ServiceInjectionProcessor(FilmDAO filmDAO,TicketDAO ticketDAO,ShowtimeDAO showtimeDAO,UserDAO userDAO,String packagePath) {

        try {

            URL url = Thread.currentThread().getContextClassLoader().getResource(packagePath.replace(".", "/"));

            File file = new File(url.toURI());

            List<Class<?>> classList = new ArrayList<>();
            for (File f : Objects.requireNonNull(file.listFiles())) {
                classList.add(Class.forName(packagePath + "." + f.getName().substring(0, f.getName().length() - 6)));
            }

            for (Class<?> c : classList) {
                if (c.isAnnotationPresent(Service.class)) {
                    Constructor<?> constructor = null;
                    int flag=0;
                    try {
                        constructor = c.getConstructor(UserDAO.class);
                        flag=1;
                    } catch (NoSuchMethodException ignored){

                    }
                    try {
                        constructor = c.getConstructor(ShowtimeDAO.class);
                        flag=2;
                    } catch (NoSuchMethodException ignored){

                    }
                    try {
                        constructor = c.getConstructor(TicketDAO.class);
                        flag=3;
                    } catch (NoSuchMethodException ignored){

                    }
                    try {
                        constructor = c.getConstructor(FilmDAO.class);
                        flag=4;
                    } catch (NoSuchMethodException ignored){

                    }
                    if(flag==0){
                        throw  new RuntimeException();
                    }
                    if(flag==1){
                        map.put(c, constructor.newInstance(userDAO));
                    }
                    if(flag==2){
                        map.put(c, constructor.newInstance(showtimeDAO));
                    }
                    if(flag==3){
                        map.put(c, constructor.newInstance(ticketDAO));
                    }
                    if(flag==4){
                        map.put(c, constructor.newInstance(filmDAO));
                    }

                }
            }
        } catch (Exception exception) {
            System.setErr(System.out);
            exception.printStackTrace();
            throw new RuntimeException();
        }
    }


    public Map<Class<?>,Object> getMap(){
        return new HashMap<>(map);
    }

    @SuppressWarnings("unchecked")
    public <T> T createCommand(Class<T> commandClass) {
        try {
            if (commandClass.getConstructors().length > 1) {
                throw new RuntimeException();
            }

            Constructor<?> constructor = commandClass.getConstructors()[0];
            if (!constructor.isAnnotationPresent(MyInject.class)) {
                throw new RuntimeException();
            }

            if (!map.keySet().containsAll(Arrays.asList(constructor.getParameterTypes()))) {
                throw new RuntimeException();
            }
            List<Object> parameters = new ArrayList<>();
            for (Class c : constructor.getParameterTypes()) {
                parameters.add(map.get(c));
            }

            return (T) constructor.newInstance(parameters.toArray());
        } catch (Exception exception) {
            System.setErr(System.out);
            exception.printStackTrace();
            throw new RuntimeException();
        }
    }


}
