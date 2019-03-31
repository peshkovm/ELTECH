package com.WebApp7.client;

import com.WebApp7.shared.BookReader;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface GreetingServiceAsync {
    void getReaderList(AsyncCallback<List<String>> callback);
    void getBookReaderList(String readerFIO, AsyncCallback<List<BookReader>> callback);
}