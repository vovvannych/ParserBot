import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {

    private static final String TOKEN = "1926460492:AAHqgmkhMIoP0QhFqOM86X_-8_c27_1ihcg";
    private static final String USERNAME = "r3b2_bot";

    public void setButtons(SendMessage sendMessage){  //метод с клавиатурой под текстовым блоком
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();  //инициализация клав-ры
        sendMessage.setReplyMarkup(replyKeyboardMarkup); //разметка для клав-ры и связка с нашим сообщением
        replyKeyboardMarkup.setSelective(true);//параметр который выводит клав-ру либо всем либо некоторым
        replyKeyboardMarkup.setResizeKeyboard(true);//подгонка клавиатуры больше меньше для клиентоса
        replyKeyboardMarkup.setOneTimeKeyboard(false);//параметр, скрывать клавиатуру после использования кнопки или не скрывать

        List<KeyboardRow> keyboardRowList = new ArrayList<>(); //создаем лист с кнопками
        KeyboardRow keyboardFirstRow = new KeyboardRow();//первая строчка клав-ры

        keyboardFirstRow.add(new KeyboardButton("/help"));//помещаем кнопку в строку
        keyboardFirstRow.add(new KeyboardButton("/setting"));//помещаем кнопку в строку
        keyboardFirstRow.add(new KeyboardButton("/parse"));//помещаем кнопку в строку

        keyboardRowList.add(keyboardFirstRow);//добавляем все строчки клав-ры в список
        replyKeyboardMarkup.setKeyboard(keyboardRowList);//устанавливаем этот список в клавиатуре
        //далее чтоб наша клав-ра заработала помещаем ее в метод сендмэсседж

    }
    private ReplyKeyboardMarkup getButtons(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();  //инициализация клав-ры
        replyKeyboardMarkup.setSelective(true);//параметр который выводит клав-ру либо всем либо некоторым
        replyKeyboardMarkup.setResizeKeyboard(true);//подгонка клавиатуры больше меньше для клиентоса
        replyKeyboardMarkup.setOneTimeKeyboard(false);//параметр, скрывать клавиатуру после использования кнопки или не скрывать

        List<KeyboardRow> keyboardRowList = new ArrayList<>(); //создаем лист с кнопками
        KeyboardRow keyboardFirstRow = new KeyboardRow();//первая строчка клав-ры

        keyboardFirstRow.add(new KeyboardButton("/help"));//помещаем кнопку в строку
        keyboardFirstRow.add(new KeyboardButton("/setting"));//помещаем кнопку в строку
        keyboardFirstRow.add(new KeyboardButton("/parse"));//помещаем кнопку в строку

        keyboardRowList.add(keyboardFirstRow);//добавляем все строчки клав-ры в список
        replyKeyboardMarkup.setKeyboard(keyboardRowList);//устанавливаем этот список в клавиатуре
        //далее чтоб наша клав-ра заработала помещаем ее в метод сендмэсседж
        return replyKeyboardMarkup;
    }

    public String getBotUsername() {
        return USERNAME;
    }

    public String getBotToken() {
        return TOKEN;
    }

    public void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true); //включаем возможность развилки
        sendMessage.setChatId(message.getChatId().toString());//Айди чата чтоб было понятно по логам кому и как боту отвечать
        sendMessage.setReplyToMessageId(message.getMessageId());  //Показываем на какое сообщение должен ответить бот
        sendMessage.setText(text); //сообщение в кот отправляли метод
        setButtons(sendMessage);
        sendMessage(sendMessage);
    }

    private void sendMessage(SendMessage sendMessage) {
    }

    public void onUpdateReceived(Update update) {
        if(update.getMessage()!=null && update.getMessage().hasText()) {
            String chat_id = update.getMessage().getChatId().toString();
            switch (update.getMessage().getText()){
                case "/help":
                    try {
                        execute(new SendMessage(chat_id, "Чем могу помочь?").setReplyMarkup(getButtons()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/settings":
                    try {
                        execute(new SendMessage(chat_id, "Что будем делать?").setReplyMarkup(getButtons()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/parse":
                    Parser1 parser1 = new Parser1();
                    try {
                        var strings = parser1.pagesGenerator();
                        execute(new SendMessage(chat_id, String.valueOf(strings.size())));
//                        for (int i = 0; i < strings.size(); i++) {
//                            execute(new SendMessage(chat_id, String.valueOf(strings.get(i).getInfo())));
//                            Thread.sleep(200);
//                        }

                    } catch (TelegramApiException | IOException | SQLException e) {
                        e.printStackTrace();
                    }
                default:
            }
        }
    }
}

