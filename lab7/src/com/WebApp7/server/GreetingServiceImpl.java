package com.WebApp7.server;

import com.WebApp7.client.GreetingService;
import com.WebApp7.shared.BookReader;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.annotation.WebServlet;
import java.util.*;

public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
    private Map<String, List<BookReader>> db = null;

    public List<String> getReaderList() {
        if (db == null) {
            initDB();
        }
        String[] tmp = new String[db.keySet().size()];
        db.keySet().toArray(tmp);
        return Arrays.asList(tmp);
    }

    public List<BookReader> getBookReaderList(String readerFIO) {
        if (db == null) {
            initDB();
        }
        return db.get(readerFIO);
    }

    private void initDB() {
        db = new HashMap<String, List<BookReader>>();
        List<BookReader> entries1 = new ArrayList<BookReader>();
        entries1.add(new BookReader("Достоевский", "Игрок", true));
        entries1.add(new BookReader("Толстой", "Анна Каренина", true));
        entries1.add(new BookReader("Достоевский", "Идиот", false));
        db.put("Иванов И. И.", entries1);
        List<BookReader> entries2 = new ArrayList<BookReader>();
        entries2.add(new BookReader("Толстой", "Анна Каренина", false));
        entries2.add(new BookReader("Достоевский", "Игрок", true));
        db.put("Петров П.П.", entries2);
        List<BookReader> entries3 = new ArrayList<BookReader>();
        entries3.add(new BookReader("Толстой", "Анна Каренина", false));
        entries3.add(new BookReader("Булгаков", "Белая гвардия", true));
        db.put("Кац И.М.", entries3);
    }
}