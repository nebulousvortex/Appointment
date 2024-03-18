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

import java.util.List;

@Service
public class BotService extends TelegramLongPollingBot {
    private TelegramBotsApi telegramBotsApi;

    @Autowired
    public BotService(TelegramBotsApi telegramBotsApi) throws TelegramApiException {
        this.telegramBotsApi = telegramBotsApi;
        this.telegramBotsApi.registerBot(this);
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
    private String specialization;
    private List<Doctor> doctors;
    private List<Ticket> tikets;
    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText() + update.getMessage().getFrom();
        SendMessage sendMessage = new SendMessage();

        if (update.getMessage().isCommand()){
            if(update.getMessage().getText().startsWith("/login ")){
                username = update.getMessage().getText().substring(7);
                sendMessage.setChatId(chatId);
                sendMessage.setText("Привет, " + username);
            }
            if(update.getMessage().getText().startsWith("/doctors ")){
                specialization = update.getMessage().getText().substring(9);
                sendMessage.setChatId(chatId);
                doctors = doctorService.findBySpecialization(specialization);
                StringBuilder textBuilder = new StringBuilder();
                for (int i = 0; i < doctors.size(); i++) {
                    Doctor doctor = doctors.get(i);
                    String doctorInfo = (i + 1) + ". " + doctor.getUser().getFirstName() + " " + doctor.getUser().getLastName() + ", " + doctor.getSpecialization();
                    textBuilder.append(doctorInfo).append("\n");
                }
                sendMessage.setText(textBuilder.toString());
            }
            if(update.getMessage().getText().startsWith("/ticket ")){
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
            if(update.getMessage().getText().startsWith("/appoint ")){
                int doctorNumber = Integer.parseInt(update.getMessage().getText().substring(9));
                sendMessage.setChatId(chatId);
                Ticket ticket = tikets.get(doctorNumber-1);
                emailService.sendEmailWithQR(userService.findByUsername(username).getMail(), username, ticket.getId());
                sendMessage.setText("Вам на почту выслан QR код. Просканируйте в течение 10 минут, чтобы подтвердить запись на прием.");
            }
        }

        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
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
