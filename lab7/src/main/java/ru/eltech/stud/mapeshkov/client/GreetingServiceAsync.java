package ru.eltech.stud.mapeshkov.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.eltech.stud.mapeshkov.shared.Formula_1RowReader;

import java.util.List;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
  void getReaderList(AsyncCallback<List<String>> callback);

  void getBookReaderList(String readerFIO, AsyncCallback<List<Formula_1RowReader>> callback);
}
