package com.dsce.reminderbot;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.stanford.nlp.simple.Document;

public class ReminderParser {
    public static Reminder parse(String input) {
        Document doc = new Document(input);

        String intent = extractIntent(doc);
        Date dateTime = extractDateTime(doc);

        return new Reminder(intent, dateTime);
    }

    private static String extractIntent(Document doc) {
        return doc.sentences().get(0).text(); // Use the whole sentence as the intent
    }

    private static Date extractDateTime(Document doc) {
        String text = doc.sentences().get(0).text().toLowerCase();

        Calendar calendar = Calendar.getInstance();

        // Example: "tomorrow at 5 PM"
        if (text.contains("tomorrow")) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Pattern pattern = Pattern.compile("\\d{1,2}\\s*(?:a\\.?m?\\.?|p\\.?m?\\.?)");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String time = matcher.group().replaceAll("\\s+", "");
                if (time.endsWith("pm")) {
                    int hour = Integer.parseInt(time.replace("pm", ""));
                    if (hour < 12) {
                        hour += 12;
                    }
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                } else {
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.replace("am", "")));
                }
            }
        }
        
        
        if (text.contains("today")) {
            Pattern pattern = Pattern.compile("\\d{1,2}\\s*(?:a\\.?m?\\.?|p\\.?m?\\.?)");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String time = matcher.group().replaceAll("\\s+", "");
                if (time.endsWith("pm")) {
                    int hour = Integer.parseInt(time.replace("pm", ""));
                    if (hour < 12) {
                        hour += 12;
                    }
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                } else {
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.replace("am", "")));
                }
            }
        }

        // Set the minutes and seconds to zero
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
