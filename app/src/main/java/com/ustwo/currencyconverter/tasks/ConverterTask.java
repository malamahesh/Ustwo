package com.ustwo.currencyconverter.tasks;

import android.os.AsyncTask;

import com.ustwo.currencyconverter.MainActivity;
import com.ustwo.currencyconverter.comms.ConnectionManager;
import com.ustwo.currencyconverter.models.Currency;

/**
 *
 */
public class ConverterTask extends AsyncTask<Void, Void, Void> {
	private MainActivity activity;
	private Currency currency;

	private ConnectionManager connectionManager;

	public ConverterTask(MainActivity activity) {
		this.activity = activity;
		currency = new Currency();
	}

	@Override
	protected Void doInBackground(Void... params) {
		JsonParser parser = new JsonParser();

		connectionManager = new ConnectionManager(activity,
				"http://api.fixer.io/latest?base=AUD");
		currency = parser.parseCurrency(connectionManager.requestJson());
		connectionManager.closeConnection();

		return null;
	}

	protected void onPostExecute(Void result) {
		activity.updateView(currency);
	}

}
