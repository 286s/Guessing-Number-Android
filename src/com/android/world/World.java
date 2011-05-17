package com.android.world;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.antonio081014.world.R;

public class World extends Activity {

	// Four distinct digits, and one counter;
	private int number1;
	private int number2;
	private int number3;
	private int number4;
	private int count;
	private int hintCount;
	private ArrayList<String> s;
	private final static int hintNumbers = 2;
	private final static int countNumbers = 8;

	// GUI component;
	private Spinner sp1;
	private Spinner sp2;
	private Spinner sp3;
	private Spinner sp4;
	private EditText tv;
	private Button submit;

	private void generateNumber() {
		Random r = new Random();
		number1 = r.nextInt(9) + 1;
		do {
			number2 = r.nextInt(9) + 1;
		} while (number1 == number2);
		do {
			number3 = r.nextInt(9) + 1;
		} while (number1 == number3 || number2 == number3);
		do {
			number4 = r.nextInt(9) + 1;
		} while (number1 == number4 || number2 == number4 || number3 == number4);
		// Toast.makeText(getApplicationContext(),
		// number1 + "" + number2 + "" + number3 + "" + number4, 3000)
		// .show();
	}

	private void newGame() {
		count = 0;
		hintCount = 0;
		s.clear();
		generateNumber();
		tv.setText("");
		sp1.setSelection(0);
		sp2.setSelection(0);
		sp3.setSelection(0);
		sp4.setSelection(0);
		submit.setEnabled(true);
		
	}

	private void check() {
		int num1 = sp1.getSelectedItem().toString().charAt(0) - '0';
		int num2 = sp2.getSelectedItem().toString().charAt(0) - '0';
		int num3 = sp3.getSelectedItem().toString().charAt(0) - '0';
		int num4 = sp4.getSelectedItem().toString().charAt(0) - '0';
		// Log.i("submit button", "Starts");
		if (num1 == num2 || num1 == num3 || num1 == num4 || num2 == num3
				|| num2 == num4 || num3 == num4) {
			Toast.makeText(getApplicationContext(),
					"All four digits should be distinct.", 4000).show();
			return;
		}
		// Log.i("submit button", "Continue");
		int a = 0;
		int b = 0;
		if (num1 == number1) {
			a++;
		} else if (num1 == number2 || num1 == number3 || num1 == number4) {
			b++;
		}
		if (num2 == number2) {
			a++;
		} else if (num2 == number1 || num2 == number3 || num2 == number4) {
			b++;
		}
		if (num3 == number3) {
			a++;
		} else if (num3 == number1 || num3 == number2 || num3 == number4) {
			b++;
		}
		if (num4 == number4) {
			a++;
		} else if (num4 == number1 || num4 == number2 || num4 == number3) {
			b++;
		}

		if (a == 4 && b == 0) {
			// Log.i("submit", "Start Toast");
			showDialogBox("Win", "Congratulations!!\nYou got the Right number!");
			submit.setEnabled(false);
		} else {
			String str = num1 + "" + num2 + "" + num3 + "" + num4
					+ ", Correct: " + a + " PartialCorrect: " + b + "\n";
			if (s.contains(str) == true) {
				Toast.makeText(getApplicationContext(),
						"You have tried this conbination already.", 4000)
						.show();
				return;
			} else {
				s.add(str);
			}
			count++;
			tv.append(str);
		}

		if (count >= countNumbers) {
			Log.i("submit", "Game Over");
			Toast.makeText(
					getApplicationContext(),
					"Game Over, you couldn't figure this out.\nThe answer is "
							+ number1 + "" + number2 + "" + number3 + ""
							+ number4 + ".\nGood luck for next time.", 5000)
					.show();
			submit.setEnabled(false);
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		
		Log.i("Layout Created", "Correct");
		s = new ArrayList<String>();
		sp1 = (Spinner) this.findViewById(R.id.spinner1);
		sp2 = (Spinner) this.findViewById(R.id.spinner2);
		sp3 = (Spinner) this.findViewById(R.id.spinner3);
		sp4 = (Spinner) this.findViewById(R.id.spinner4);
		tv = (EditText) this.findViewById(R.id.displayTextView);
		submit = (Button) this.findViewById(R.id.submitbt);

		Log.i("onCreate", "Get ID Successfully.");

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.number, android.R.layout.simple_spinner_item);
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(adapter);
		sp2.setAdapter(adapter);
		sp3.setAdapter(adapter);
		sp4.setAdapter(adapter);
		newGame();

		Log.i("Initial", "Generate the numbers successfully.");

		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				check();
			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.newgame:
			newGame();
			return true;
		case R.id.help:
			showHelp();
			return true;
		case R.id.hint:
			showHint();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showHint() {
		Random r = new Random();
		int n = r.nextInt(4);
		String title = "Hint";
		String msg = "It has number: ";
		if (hintCount >= hintNumbers) {
			showDialogBox(title, "No more hints");
			return;
		}
		switch (n) {
		case 0:
			msg += number1;
			break;
		case 1:
			msg += number2;
			break;
		case 2:
			msg += number3;
			break;
		case 3:
			msg += number4;
			break;
		}
		hintCount++;
		showDialogBox(title, msg);
	}

	private void showHelp() {
		String msg = "All you have is 8 chances to get the right four distinct digits ranged from 1 to 9.\n";
		msg += "1. Partial Correct demostrate the right digits but at the wrong positions;\n";
		msg += "2. Correct demostrate the right digits at the right positions;\n";
		msg += "3. Record will be showed at the bottom.\n";
		msg += "4. Random hint will be showed for 2 times at most if you clicked the hint menu.\n";
		String title = "Help Guide";
		showDialogBox(title, msg);
	}

	private void showDialogBox(String title, String msg) {
		AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(msg);
		alertDialog.setButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
		alertDialog.show();
	}
}