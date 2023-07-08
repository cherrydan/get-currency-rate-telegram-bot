package com.example.getcurrencyratetelegrambot.service;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CurrencyService {

    public static String getCurrencyRate(String message, CurrencyModel model) throws IOException, ParseException {
        @Deprecated(since="20")
        URL url = new URL("https://www.nbrb.by/api/exrates/rates/" + message + "?parammode=2");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext())  {
            result+= scanner.nextLine();
        }
        JSONObject jsonObject = new JSONObject(result);
        model.setCur_ID(jsonObject.getInt("Cur_ID"));
        model.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(jsonObject.getString("Date")));
        model.setCur_Abbreviation(jsonObject.getString("Cur_Abbreviation"));
        model.setCur_Scale(jsonObject.getInt("Cur_Scale"));
        model.setCur_Name(jsonObject.getString("Cur_Name"));
        model.setCur_OfficialRate(jsonObject.getDouble("Cur_OfficialRate"));

        return "Official rate of BYN to " + model.getCur_Abbreviation() + "\n" +
                "on the date: " + getFormatDate(model) + "\n" +
                "is: " + model.getCur_OfficialRate() + " BYN per " + model.getCur_Scale() + " " + model.getCur_Abbreviation();

    }

    private static String getFormatDate(CurrencyModel model) {
        return new SimpleDateFormat("dd MMM yyyy").format(model.getDate());
    }


}
