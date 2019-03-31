package ru.eltech.stud.mapeshkov.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ru.eltech.stud.mapeshkov.shared.Formula_1RowReader;

import java.util.List;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
  List<String> getReaderList();

  List<Formula_1RowReader> getBookReaderList(String readerFIO);
}
