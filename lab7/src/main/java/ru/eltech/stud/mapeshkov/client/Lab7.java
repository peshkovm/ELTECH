package ru.eltech.stud.mapeshkov.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.eltech.stud.mapeshkov.shared.Formula_1RowReader;

import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Lab7 implements EntryPoint {
    private static final String SRV_ERR = "Ошибка сервера! ";
    private static final String SRV_ERR_GET_STAGES_LIST = "Невозможно получить список этапов.";
    private static final String SRV_ERR_GET_FORMULA_1_LIST = "Невозможно получить этапы Формула-1 2017.";
    private static final String GET_FORMULA_1_LIST_BTN = "Получить этапы Формула-1 2017.";
    private static final String CLOSE_BTN = "Закрыть";
    private static final String STAGES_WND_TITLE = "Список этапов Формула-1 2017 ";
    /**
     * RPC-сервис
     */
    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);

    public void onModuleLoad() {
        final ListBox readerListBox = new ListBox(false);
        final MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
        final Label errorLabel = new Label();
        final Button sendButton = new Button(GET_FORMULA_1_LIST_BTN);
        sendButton.addStyleName("sendButton");
        RootPanel.get("readerListBoxContainer").add(readerListBox);
        RootPanel.get("errorLabelContainer").add(errorLabel);
        RootPanel.get("sendButtonContainer").add(sendButton);
        readerListBox.setFocus(true);
        greetingService.getReaderList(new AsyncCallback<List<String>>() {
            public void onFailure(Throwable caught) {
                errorLabel.setText(SRV_ERR + SRV_ERR_GET_STAGES_LIST);
            }

            public void onSuccess(List<String> result) {
                oracle.clear();
                oracle.addAll(result);

                ////////////sort result/////////
                result.sort((stage1, stage2) -> {
                    int stageNum1 = Integer.parseInt(stage1.split(" ")[0]);
                    int stageNum2 = Integer.parseInt(stage2.split(" ")[0]);

                    return stageNum1 - stageNum2;
                });
                ////////////////////////////////

                for (String r : result) {
                    readerListBox.addItem(r);
                }
            }
        });
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText(STAGES_WND_TITLE);
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button(CLOSE_BTN);
        closeButton.getElement().setId("closeButton");
        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        final CellTable<Formula_1RowReader> table = createCellTable();
        dialogVPanel.add(table);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                sendButton.setEnabled(true);
                sendButton.setFocus(true);
            }
        });
        class RPCClickHandler implements ClickHandler, KeyUpHandler {
            public void onClick(ClickEvent event) {
                sendReaderFIOToServer();
            }

            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    sendReaderFIOToServer();
                }
            }

            private void sendReaderFIOToServer() {
                errorLabel.setText("");
                final String readerFIO = readerListBox.getValue(readerListBox.getSelectedIndex());
                sendButton.setEnabled(false);
                greetingService.getBookReaderList(readerFIO, new AsyncCallback<List<Formula_1RowReader>>() {
                    public void onFailure(Throwable caught) {
                        dialogBox.setText(SRV_ERR);
                        serverResponseLabel
                                .addStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(SRV_ERR + SRV_ERR_GET_FORMULA_1_LIST);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    public void onSuccess(List<Formula_1RowReader> result) {
                        dialogBox.setText(STAGES_WND_TITLE + readerFIO);
                        table.setRowCount(result.size(), true);
                        table.setRowData(0, result);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }
                });
            }
        }
        RPCClickHandler handler = new RPCClickHandler();
        sendButton.addClickHandler(handler);
    }

/*    private CellTable<BookReader> createCellTable() {
        final CellTable<BookReader> table = new CellTable<BookReader>();
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        TextColumn<BookReader> authorColumn = new TextColumn<BookReader>() {
            public String getValue(BookReader object) {
                return object.getAuthor();
            }
        };
        table.addColumn(authorColumn, "Дата, время");
        TextColumn<BookReader> titleColumn = new TextColumn<BookReader>() {
            public String getValue(BookReader object) {
                return object.getTitle();
            }
        };
        table.addColumn(titleColumn, "Этап");
        TextColumn<BookReader> isReadColumn = new TextColumn<BookReader>() {
            public String getValue(BookReader object) {
                return object.isReader() ? "Да" : "Нет";
            }
        };
        table.addColumn(isReadColumn, "");

        return table;
    }*/

    private CellTable<Formula_1RowReader> createCellTable() {
        final CellTable<Formula_1RowReader> table = new CellTable<>();
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        TextColumn<Formula_1RowReader> dateTimeColumn = new TextColumn<Formula_1RowReader>() {
            public String getValue(Formula_1RowReader object) {
                return object.getDateAndTime();
            }
        };
        table.addColumn(dateTimeColumn, "Дата, время");
        TextColumn<Formula_1RowReader> stageColumn = new TextColumn<Formula_1RowReader>() {
            public String getValue(Formula_1RowReader object) {
                return object.getStage();
            }
        };
        table.addColumn(stageColumn, "Этап");
        TextColumn<Formula_1RowReader> descriptionColumn = new TextColumn<Formula_1RowReader>() {
            public String getValue(Formula_1RowReader object) {
                return object.getDescription();
            }
        };
        table.addColumn(descriptionColumn, "");

        return table;
    }
}
