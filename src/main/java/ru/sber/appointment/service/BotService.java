package ru.sber.appointment.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Ticket;
import ru.sber.appointment.model.User;

import java.util.List;

@Service
public class BotService extends TelegramLongPollingBot {

    @Autowired
    public BotService(TelegramBotsApi telegramBotsApi) throws TelegramApiException {
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
    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        SendMessage sendMessage = new SendMessage();

        if (update.getMessage().isCommand()){
            if(update.getMessage().getText().startsWith("/login")){
                login(update, sendMessage, chatId);
            }
            if(update.getMessage().getText().startsWith("/doctors")){
                getDoctors(update, sendMessage, chatId);
            }
            if(update.getMessage().getText().startsWith("/ticket")){
                getTickets(update, sendMessage, chatId);
            }
            if(update.getMessage().getText().startsWith("/appoint")){
                sendQR(update, sendMessage, chatId);
            }
        }

        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendQR(Update update, SendMessage sendMessage, String chatId) {
        if(validate(sendMessage, chatId)) {
            int doctorNumber = Integer.parseInt(update.getMessage().getText().substring(9));
            sendMessage.setChatId(chatId);
            Ticket ticket = tikets.get(doctorNumber - 1);
            emailService.sendEmailWithQR(userService.findByUsername(username).getMail(), username, ticket.getId());
            sendMessage.setText("Вам на почту выслан QR код. Просканируйте в течение 10 минут, чтобы подтвердить запись на прием.");
        }
    }

    private void getTickets(Update update, SendMessage sendMessage, String chatId) {
        int doctorNumber = Integer.parseInt(update.getMessage().getText().substring(8));
        sendMessage.setChatId(chatId);
        tikets = ticketService.findDoctorFreeTicket(doctors.get(doctorNumber - 1));
        StringBuilder textBuilder = new StringBuilder();
        int size = 0;
        if (tikets.size()>20){
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

    private void getDoctors(Update update, SendMessage sendMessage, String chatId) {
        if (update.getMessage().getText().length() > 9 ) {
            String specialization = update.getMessage().getText().substring(9);
            doctors = doctorService.findBySpecialization(specialization);
        }
        sendMessage.setChatId(chatId);
        if(doctors == null || doctors.isEmpty()){
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

    private void login(Update update, SendMessage sendMessage, String chatId) {
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
        return "7159491936:AAHSTv9dgqGJSwHFgpCgA6OpncflLafIwy8";
    }

    @Override
    public String getBotUsername() {
        return "AppointmentBot";
    }
}
