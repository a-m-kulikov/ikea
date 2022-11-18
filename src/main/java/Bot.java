import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import Exception.NotAnArticleNumberException;

public class Bot extends TelegramLongPollingBot {

    private static final String API_TOKEN = "5760381979:AAHz1r9cnOWByrlNZt7vhwFtI5-OOFiv35M";
    private static final String BOT_USERNAME = "IKEA_ANALOG_RUSSIA";

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return API_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        User user = message.getFrom();
        try {
            String articleNumber = arcticleNumberFormatting(message.getText());
            sendText(user.getId(),"К сожалению, на данный момент аналога этого товара IKEA нет в нашей базе данных");
        } catch (NotAnArticleNumberException e) {
            sendText(user.getId(), e.getMessage());
        }
    }

    private String arcticleNumberFormatting(String message) throws NotAnArticleNumberException {
        String charsToRemove = " .";
        String arcticleNumber = message;
        for (char c : charsToRemove.toCharArray()) {
            arcticleNumber = arcticleNumber.replace(String.valueOf(c), "");
        }
        if (arcticleNumber.length() == 8) {
            return arcticleNumber;
        }
        else throw new NotAnArticleNumberException();
    }

    public void sendText(Long userId, String message){
        SendMessage sendMessage = SendMessage.builder()
                .chatId(userId.toString())
                .text(message).build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
