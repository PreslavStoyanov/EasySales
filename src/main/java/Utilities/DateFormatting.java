package Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateFormatting {
    public static boolean isValidDateFormat(String date) {
        Pattern pattern = Pattern.compile("[0-9]{2}:[0-9]{2}:[0-9]{4}");
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static Date getDate(String stringDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd:MM:yyyy").parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
