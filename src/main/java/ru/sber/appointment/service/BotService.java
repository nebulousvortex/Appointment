package ru.sber.appointment.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.sber.appointment.exception.TelegramMessageException;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Ticket;
import ru.sber.appointment.model.User;

import java.util.List;

/**
 * Сервис для работы с Telegram ботом.
 */
@Service
public class BotService extends TelegramLongPollingBot {

    String token;
    String name;
    String qrSend;
    @Autowired
    public BotService(TelegramBotsApi telegramBotsApi,
                      @Value("${telegram.token}") String token,
                      @Value("${telegram.name}") String name,
                      @Value("${message.qrsend}") String qrSend) throws TelegramApiException {
        this.token = token;
        this.name = name;
        this.qrSend = qrSend;
        telegramBotsApi.registerBot(this);
    }

    @Autowired
    DoctorServiceImpl doctorService;
    @Autowired
    TicketServiceImpl ticketService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    EmailServiceImpl emailService;
    private String username;
    private List<Doctor> doctors;
    private List<Ticket> tikets;

    /**
     * Метод для обработки обновлений от пользователя.
     * @param update объект с информацией об обновлении
     */
    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        SendMessage sendMessage = new SendMessage();
        if (update.getMessage().isCommand()){
            login(update, sendMessage, chatId);
            getDoctors(update, sendMessage, chatId);
            getTickets(update, sendMessage, chatId);
            sendQR(update, sendMessage, chatId);
        } else {
            sendMessage.setText("Не понимаю вас!!!");
        }
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new TelegramMessageException("Некорректный формат сообщения" + e);
        }
    }

    private void sendQR(Update update, SendMessage sendMessage, String chatId) {
        if(update.getMessage().getText().startsWith("/appoint")) {
            if (validate(sendMessage, chatId)) {
                int doctorNumber = Integer.parseInt(update.getMessage().getText().substring(9));
                sendMessage.setChatId(chatId);
                Ticket ticket = tikets.get(doctorNumber - 1);
                if(ticket.getUser() == null) {
                    emailService.sendEmailWithQR(userService.findByUsername(username).getMail(), username, ticket.getId());
                    sendMessage.setText(qrSend);
                } else {
                    sendMessage.setText("Талон занят.");

                }
            }
        }
    }

    private void getTickets(Update update, SendMessage sendMessage, String chatId) {
        if(update.getMessage().getText().startsWith("/ticket")) {
            int doctorNumber = Integer.parseInt(update.getMessage().getText().substring(8));
            sendMessage.setChatId(chatId);
            tikets = ticketService.findDoctorFreeTicket(doctors.get(doctorNumber - 1));
            StringBuilder textBuilder = new StringBuilder();
            int size = 0;
            if (tikets.size() > 20) {
                size = 20;
            } else {
                size = tikets.size();
            }
            for (int i = 0; i < size; i++) {
                Ticket ticket = tikets.get(i);
                String ticketInfo = (i + 1) + ". Дата: " + ticket.getSchedule().getDate() + " Время:  " + ticket.getTime();
                textBuilder.append(ticketInfo).append("\n");
            }
            sendMessage.setText(textBuilder.toString());
        }
    }

    private void getDoctors(Update update, SendMessage sendMessage, String chatId) {
        if(update.getMessage().getText().startsWith("/doctors")) {
            if (update.getMessage().getText().length() > 9) {
                String specialization = update.getMessage().getText().substring(9);
                doctors = doctorService.findBySpecialization(specialization);
            }
            sendMessage.setChatId(chatId);
            if (doctors == null || doctors.isEmpty()) {
                doctors = doctorService.findAllDoctors();
            }
            StringBuilder textBuilder = new StringBuilder();
            for (int i = 0; i < doctors.size(); i++) {
                Doctor doctor = doctors.get(i);
                String doctorInfo = (i + 1) + ". " + doctor.getUser().getFirstName() + " " + doctor.getUser().getLastName() + ", " + doctor.getSpecialization();
                textBuilder.append(doctorInfo).append("\n");
            }
            sendMessage.setText(textBuilder.toString());
        }
    }

    private void login(Update update, SendMessage sendMessage, String chatId) {
        if(update.getMessage().getText().startsWith("/login")) {
            username = update.getMessage().getText().substring(7);
            sendMessage.setChatId(chatId);
            User user = userService.findByUsername(username);
            if (user == null) {
                sendMessage.setText("Пользователь не найден!");
                username = null;
            } else {
                sendMessage.setText("Здравствуйте, " + user.getFirstName() + ' ' + user.getLastName() + '!');
            }
        }
    }

    private boolean validate(SendMessage sendMessage, String chatId){
        sendMessage.setChatId(chatId);
        if (username == null | doctors == null | tikets == null){
            sendMessage.setText("Вы заполнили не все данные для записи");
            return false;
        }
        return true;
    }

    @Override
    public String getBotToken(){
        return token;
    }

    @Override
    public String getBotUsername() {
        return name;
    }
}
