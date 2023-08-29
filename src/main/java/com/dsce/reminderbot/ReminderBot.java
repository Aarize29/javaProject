package com.dsce.reminderbot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;  


public class ReminderBot {
    private static List<Reminder> reminders = new ArrayList<>();
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("ReminderBot: How can I assist you?");
        
        while (true) {
            System.out.print("\nYou: ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("exit")) {
                System.out.println("ReminderBot: Goodbye!");
                break;
            }

            if (input.equals("show reminders")) {
                showReminders();
            } else {
                Reminder reminder = ReminderParser.parse(input);
//                System.out.println(reminder.getMessage() + "--->" + reminder.getDateTime());
                if (reminder != null) {
                    reminders.add(reminder);
                    scheduleReminder(reminder);
                    System.out.println("ReminderBot: Reminder set: " + reminder.getMessage() + " on " + reminder.getDateTime());
                } else {
                    System.out.println("ReminderBot: Sorry, I couldn't understand the input.");
                }
            }
        }

        // Shutdown the scheduler gracefully when exiting
          scanner.close();
    }

    private static void scheduleReminder(final Reminder reminder) {
        long delay = reminder.getDateTime().getTime() - new Date().getTime();
        if (delay > 0) {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    notifyReminder(reminder);
                }
            }, delay, TimeUnit.MILLISECONDS);
        }
    }


    private static void notifyReminder(Reminder reminder) {
//    	String message = "ReminderBot: Reminder! " + reminder.getMessage() + " at " + reminder.getDateTime();
    	String message = "ReminderBot: Reminder! " + reminder.getMessage();
    	showMessage(message);
    	System.out.println();
        System.out.println(message);
    }

    private static void showReminders() {
        System.out.println("ReminderBot: Here are your reminders:");
        for (Reminder reminder : reminders) {
            System.out.println(" - " + reminder.getMessage() + " on " + reminder.getDateTime());
        }
    }
    
    public static void showMessage(String message) {
    	JFrame f =new JFrame();  
    	JOptionPane.showMessageDialog(f,message);  
    }
}
