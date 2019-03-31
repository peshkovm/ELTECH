package com.WebApp7.client;

import com.WebApp7.shared.BookReader;
import com.google.gwt.user.client.rpc.RemoteService;

import java.util.List;

public interface GreetingService extends RemoteService {
    List<String> getReaderList();
    List<BookReader> getBookReaderList(String readerFIO);
}