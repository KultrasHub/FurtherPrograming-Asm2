package Backend.Project.TaxiCompany.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Configuration
public class DateTimeFormatConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }
    //return zone date time of the start of the month and last of the month
    public static ZonedDateTime[] getTimeStone(String month)
    {
        //initial
        String firstDString="1,"+month;
        String lastDString="30,"+month;//does not result the absolute correct value
        //formatter
        SimpleDateFormat formatter=new SimpleDateFormat("dd,MMMM,yyyy");
        try {
            Date firstDate = (Date) formatter.parse(firstDString);
            Date lastDate=(Date) formatter.parse(lastDString);
            //Covert date to ZoneDateTime
            ZonedDateTime fD = ZonedDateTime.ofInstant(firstDate.toInstant(), ZoneId.systemDefault());
            ZonedDateTime lD = ZonedDateTime.ofInstant(lastDate.toInstant(), ZoneId.systemDefault());
            //return the result
           ZonedDateTime[] list={fD,lD};
           return list;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static ZonedDateTime String2Time(String s)
    {
        SimpleDateFormat formatter=new SimpleDateFormat("dd,MMMM,yyyy");
        try {
            Date date = (Date) formatter.parse(s);
            return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ZonedDateTime.now();
    }

}
