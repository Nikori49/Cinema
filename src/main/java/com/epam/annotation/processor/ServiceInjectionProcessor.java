package com.epam.annotation.processor;

import com.epam.annotation.MyInject;
import com.epam.annotation.Service;
import com.epam.dao.DBManager;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.*;

public class ServiceInjectionProcessor {
    private final DBManager dbManager;

    private final HashMap<Class<?>, Object> map = new HashMap();

    public ServiceInjectionProcessor(DBManager dbManager,String packagePath) {
        this.dbManager = dbManager;
        try {

            URL url = Thread.currentThread().getContextClassLoader().getResource(packagePath.replace(".", "/"));
            System.out.println(url);
            File file = new File(url.toURI());

            List<Class<?>> classList = new ArrayList<>();
            for (File f : Objects.requireNonNull(file.listFiles())) {
                classList.add(Class.forName(packagePath + "." + f.getName().substring(0, f.getName().length() - 6)));
            }

            for (Class<?> c : classList) {
                if (c.isAnnotationPresent(Service.class)) {
                    Constructor<?> constructor = c.getConstructor(DBManager.class);
                    map.put(c, constructor.newInstance(dbManager));
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
