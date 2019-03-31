package ru.eltech.stud.mapeshkov.server;

import ru.eltech.stud.mapeshkov.client.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ru.eltech.stud.mapeshkov.shared.Formula_1RowReader;

import java.util.*;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

    private Map<String, List<Formula_1RowReader>> db = null;

    public List<String> getReaderList() {
        if (db == null) {
            initDB();
        }
        String[] tmp = new String[db.keySet().size()];
        db.keySet().toArray(tmp);
        return Arrays.asList(tmp);
    }

    public List<Formula_1RowReader> getBookReaderList(String readerFIO) {
        if (db == null) {
            initDB();
        }
        return db.get(readerFIO);
    }

    private void initDB() {
/*        db = new HashMap<String, List<BookReader>>();
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
        db.put("Кац И.М.", entries3);*/

        db = new HashMap<>();
        List<Formula_1RowReader> stage1 = new ArrayList<>();
        stage1.add(new Formula_1RowReader("1 декабпя 2017", "1 этап", "гонка в России"));
        db.put("1 этап", stage1);
        List<Formula_1RowReader> stage2 = new ArrayList<>();
        stage2.add(new Formula_1RowReader("2 декабпя 2017", "2 этап", "гонка в Финляндии"));
        db.put("2 этап", stage2);
        List<Formula_1RowReader> stage3 = new ArrayList<>();
        stage3.add(new Formula_1RowReader("3 декабпя 2017", "3 этап", "гонка в Японии"));
        db.put("3  этап", stage3);
        List<Formula_1RowReader> stage4 = new ArrayList<>();
        stage4.add(new Formula_1RowReader("4 декабпя 2017", "4 этап", "гонка в Норвегии"));
        db.put("4 этап", stage4);
        List<Formula_1RowReader> stage5 = new ArrayList<>();
        stage5.add(new Formula_1RowReader("5 декабпя 2017", "5 этап", "гонка в Америке"));
        db.put("5 этап", stage5);
    }
}