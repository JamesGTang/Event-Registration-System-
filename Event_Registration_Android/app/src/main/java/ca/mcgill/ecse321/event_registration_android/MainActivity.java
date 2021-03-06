package ca.mcgill.ecse321.event_registration_android;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;
import ca.mcgill.ecse321.eventregistration.controller.InvalidInputException;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;

public class MainActivity extends AppCompatActivity {
    private RegistrationManager rm = null;
    private String fileName;
    String error = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add xstream file configuratiin
        fileName = getFilesDir().getAbsolutePath() + "/eventregistration.xml";
        rm = PersistenceXStream.initializeModelManager(fileName);
        refreshData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshData(){
        // Todo implement refreshData method
        TextView participantField = (TextView) findViewById(R.id.participant_field);
        participantField.setText("");
        TextView eventField=(TextView) findViewById(R.id.event_field);
        eventField.setText("");
    }
    public void addParticipant(View v) {
        TextView feedback = (TextView) findViewById(R.id.feedback_textview);
        EditText participant_name=(EditText)findViewById(R.id.participant_field);
        EventRegistrationController pc = new EventRegistrationController(rm);
        try {
            pc.createParticipant(participant_name.getText().toString());
            feedback.setTextColor(Color.BLUE);
            feedback.setText("Successfully created: "+participant_name.getText().toString()+" as a participant");
        } catch (InvalidInputException e) {
            error = e.getMessage();
            feedback.setTextColor(Color.RED);
            feedback.setText(error);
        }
        refreshData();
    }
    public void addEvent(View e){
        TextView feedback=(TextView)findViewById(R.id.feedback_textview);
        EditText event_name=(EditText)findViewById(R.id.event_field);
        EventRegistrationController pc=new EventRegistrationController(rm);
        try{
            //// TODO: 2017-02-01 Implement the addEventMethod 
        }
    }
    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText());
        args.putInt("id", v.getId());
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    private Bundle getTimeFromLabel(CharSequence text){
        Bundle rtn=new Bundle();
        String comps[]=text.toString().split(":");
        int hour=12;
        int minute=0;
        if(comps.length==2){
            hour=Integer.parseInt(comps[0]);
            minute=Integer.parseInt(comps[1]);
        }
        rtn.putInt("hour",hour);
        rtn.putInt("Minute",minute);
        return rtn;
    }
    private Bundle getDateFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;
        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }
        rtn.putInt("day", day);
        rtn.putInt("month", month-1);
        rtn.putInt("year", year);
        return rtn;
    }
    public void setTime(int id, int h, int m) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d:%02d", h, m));
    }
    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }
    


}
