package com.ustwo.currencyconverter.tasks;

import java.io.IOException;
import java.util.ArrayList;

import com.ustwo.currencyconverter.models.Currency;

import android.util.JsonReader;
import android.util.JsonToken;

/**
 *
 */
public class JsonParser {

	public static final String JSON_KEY_RATES = "rates";
	public static final String JSON_KEY_CAD = "CAD";
	public static final String JSON_KEY_EUR = "EUR";
	public static final String JSON_KEY_GBP = "GBP";
	public static final String JSON_KEY_JPY = "JPY";
	public static final String JSON_KEY_USD = "USD";

	public JsonParser() {
	}

	public Currency parseCurrency(JsonReader jsonReader) {
		Currency currency = new Currency();

		try {
			jsonReader.beginObject();
			while (jsonReader.hasNext()) {
				String name = jsonReader.nextName();
				if (name.equals(JSON_KEY_RATES) && jsonReader.peek() != JsonToken.NULL) {

					jsonReader.beginObject();
					//while (jsonReader.hasNext()) {
						currency = parseInfo(jsonReader);
					//}
					jsonReader.endObject();

				} else {
					jsonReader.skipValue();
				}
			}
			jsonReader.endObject();

		} catch (IOException e) {
            e.printStackTrace();
		}

		return currency;
	}

	public Currency parseInfo(JsonReader jsonReader) {
		Currency info = new Currency();

		try {
			//jsonReader.beginArray();
			while (jsonReader.hasNext()) {
				String name = "";
				String value = "";
				try {
					name = jsonReader.nextName();
					value = jsonReader.nextString();
				} catch (Exception e) {
                    e.printStackTrace();
				}
				if (value != null && value.length() > 0) {
					if (name.equals(JSON_KEY_CAD)) {
						info.setCAD(value);
					} else if (name.equals(JSON_KEY_EUR)) {
						info.setEUR(value);
					} else if (name.equals(JSON_KEY_GBP)) {
						info.setGBP(value);
					} else if (name.equals(JSON_KEY_JPY)) {
						info.setJPY(value);
					} else if (name.equals(JSON_KEY_USD)) {
						info.setUSD(value);
					}
				}
			}

			if(info == null){
				return null;
			}
		} catch (IOException e) {
            e.printStackTrace();
		}

		return info;
	}

}
