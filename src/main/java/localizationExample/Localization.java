package localizationExample;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {

    public static void main(String[] args) {
        Locale defaultLocale = Locale.getDefault();
        Locale locale = new Locale("ja", "JP");
        Date currentDate = new Date();
        DateFormat timeFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale);

        Locale usedLocale = locale;

        System.out.println(usedLocale.getDisplayCountry());
        System.out.println(usedLocale.getDisplayLanguage());
        System.out.println(usedLocale.getDisplayName());
        System.out.println(usedLocale.getISO3Country());
        System.out.println(usedLocale.getISO3Language());
        System.out.println(usedLocale.getLanguage());


        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", usedLocale);
        System.out.println(resourceBundle.getString("greeting"));
        System.out.println(resourceBundle.getString("farewell"));
        System.out.println(timeFormat.format(currentDate));
    }
}
