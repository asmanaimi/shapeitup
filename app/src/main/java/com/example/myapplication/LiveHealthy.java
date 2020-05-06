package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class LiveHealthy extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText eWeight, eHeight, eInches, eAge,activityrate;
    TextView tweightlabel,bodytype;
    TextView tBMILable, tBMIResult, tBMRLable, tBMRResult;
    TextView tcalories,tcaloriesresult;
    Button bcalculate, bclear,bdietplan;
    float weight,value,age,BMI,inches,resultf,resultfb,h,hm,i,wpounds,r1,calorieintake;
    int rate;
    String heightf;
    double result,bmrMen,bmrWomen,kg,weightinpounds;
    RadioButton rmale,rfemale;
    Spinner sp;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AboutUs.OnFragmentInteractionListener mListener;

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }



    public interface OnFragmentInteractionListener {
    }
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        getActivity().setTitle("Pedometer");
        View vw=inflater.inflate(R.layout.fragment_livehealthy, container, false);

        tweightlabel = (TextView) vw.findViewById(R.id.textView);
        tBMILable = (TextView) vw.findViewById(R.id.textView9);
        tBMIResult = (TextView) vw.findViewById(R.id.textView10);
        tBMRLable = (TextView) vw.findViewById(R.id.textView11);
        tBMRResult = (TextView) vw.findViewById(R.id.textView12);
        tcalories = (TextView) vw.findViewById(R.id.textView15);
        tcaloriesresult = (TextView) vw.findViewById(R.id.textView14);
        bodytype = (TextView) vw.findViewById(R.id.textView13);
        eWeight = (EditText) vw.findViewById(R.id.editText);
        eHeight = (EditText) vw.findViewById(R.id.editText2);
        eInches = (EditText) vw.findViewById(R.id.editText3);
        eAge = (EditText) vw.findViewById(R.id.editText4);
        activityrate = (EditText) vw.findViewById(R.id.editText16);
        bcalculate = (Button) vw.findViewById(R.id.button);
        bclear = (Button) vw.findViewById(R.id.button2);
        bdietplan = (Button)vw.findViewById(R.id.button3);
        rmale = (RadioButton)vw.findViewById(R.id.radioButton);
        rfemale = (RadioButton)vw.findViewById(R.id.radioButton1);
        bdietplan.setVisibility(INVISIBLE);
        rfemale.setChecked(true);
        //rfemale.setChecked(true);
        //sp = (Spinner) findViewById(R.id.spinner);

        bclear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                eWeight.setText("");
                eWeight.setError(null);
                eHeight.setText("");
                eHeight.setError(null);
                eInches.setText(" ");
                eInches.setError(null);
                eAge.setText(" ");
                eAge.setError(null);
                activityrate.setText("");
                activityrate.setError(null);
                tBMIResult.setVisibility(INVISIBLE);
                tBMILable.setVisibility(INVISIBLE);
                bodytype.setVisibility(INVISIBLE);
                tBMRLable.setVisibility(INVISIBLE);
                tBMIResult.setVisibility(INVISIBLE);
                tBMRResult.setVisibility(INVISIBLE);
                rmale.setChecked(false);
                rmale.setEnabled(true);
                rfemale.setChecked(false);
                rfemale.setEnabled(true);
                tcalories.setVisibility(INVISIBLE);
                tcaloriesresult.setVisibility(INVISIBLE);
                bdietplan.setVisibility(INVISIBLE);
                rfemale.setChecked(true);

            }
        });


        bcalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (eWeight.length() == 0||eHeight.length()==0 ||eAge.length()==0||activityrate.length()==0) {
                    if(eWeight.length() == 0)
                    {
                        eWeight.setError("Enter Weight");
                    }
                    else if(eHeight.length()==0)
                    {
                        eHeight.setError("Enter the height");
                    }
                    else if(eAge.length()==0)
                    {
                        eAge.setError("Enter the age");
                    }
                    else if (activityrate.length()==0)
                    {
                        activityrate.setError("Enter the activity");
                    }

                }


                else{
                    if(eInches.length()==0)
                    {

                        eInches.setText((String.valueOf(0)));
                        weight = Float.parseFloat(eWeight.getText().toString());
                        h = Float.parseFloat(eHeight.getText().toString());
                        i = Float.parseFloat(eInches.getText().toString());
                        age = Float.parseFloat(eAge.getText().toString());
                        rate = Integer.parseInt(activityrate.getText().toString());
                        String srate = activityrate.getText().toString();
                        optionsChecked();

                        calculateBMI();
                        calculateBMR();
                        bdietplan.setVisibility(VISIBLE);
                    }
                    else
                    {
                        weight = Float.parseFloat(eWeight.getText().toString());
                        h = Float.parseFloat(eHeight.getText().toString());
                        i = Float.parseFloat(eInches.getText().toString());
                        age = Float.parseFloat(eAge.getText().toString());
                        rate = Integer.parseInt(activityrate.getText().toString());
                        String srate = activityrate.getText().toString();
                        optionsChecked();

                        calculateBMI();
                        calculateBMR();
                        bdietplan.setVisibility(VISIBLE);
                    }

                }

            }
        });

        bdietplan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("bmi is","value"+resultfb);
                optionsChecked();

                if(resultfb<= 18.5) {
                    Intent t = new Intent(getContext(), DietPlan.class);
                    String s = Float.toString(calorieintake);
                    Log.d("bmi is","value"+calorieintake);
                    t.putExtra("calories", s);
                    startActivityForResult(t, 0);
                }
                else if (resultfb >= 18.5 && resultfb <=24.9)
                {
                    Intent t1 = new Intent(getContext(), NormalWeight.class);
                    String s = Float.toString(calorieintake);
                    Log.d("bmi is","value"+calorieintake);
                    t1.putExtra("calories", s);
                    startActivityForResult(t1, 0);
                }
                else if (resultfb >= 25.0 && resultfb <= 29.9){

                    Intent t1 = new Intent(getContext(), OverWeight.class);
                    String s = Float.toString(calorieintake);
                    Log.d("bmi is","value"+calorieintake);
                    t1.putExtra("calories", s);
                    startActivityForResult(t1, 0);

                }
                else if ((resultfb >= 30.0))
                {
                    Intent t1 = new Intent(getContext(), Obese.class);
                    String s = Float.toString(calorieintake);
                    t1.putExtra("calories", s);
                    Log.d("bmi is","value"+calorieintake);
                    startActivityForResult(t1, 0);
                }
            }
        });
        return vw ;
    }


  //  @Override
  /*  public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       switch(item.getItemId()) {
            case R.id.mnuMain:
                startActivity(new Intent(getContext(), HomeActivity.class));
                return true;
            case R.id.mnuExit:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    public void calculateBMI(){
        wpounds = (float)0.45;
        wpounds = weight* wpounds;
        //Log.d("weightinpounds","value" + wpounds);
        optionsChecked();
        hm = h*12;
        hm = hm+i;
        float hinches = (float)0.025;
        float heightininches = hm*hinches;
        float sinches = heightininches *heightininches;
        //Log.d("sinches","value" + sinches);
        BMI = wpounds /sinches;
        //Log.d("BMI","BMI" + BMI);
        result = (float)(Math.round(BMI * 100.0) / 100.0);
        resultfb = (float)result;
        tBMIResult.setVisibility(VISIBLE);
        tBMIResult.setTextSize(18);
        tBMIResult.setTypeface(tBMIResult.getTypeface(), Typeface.BOLD);
        tBMIResult.setText(Float.toString(resultfb));
        tBMILable.setVisibility(VISIBLE);
        tBMILable.setTextSize(18);
        tBMILable.setTypeface(tBMILable.getTypeface(), Typeface.BOLD);
        displayBodyType();

    }

    public void displayBodyType(){
        optionsChecked();
        if(resultfb<= 18.5){
            bodytype.setVisibility(VISIBLE);
            bodytype.setTextSize(18);
            bodytype.setTypeface(bodytype.getTypeface(), Typeface.BOLD);
            bodytype.setText("UnderWeight");
        }
        else if (resultfb >= 18.5 && resultfb <=24.9){
            bodytype.setVisibility(VISIBLE);
            bodytype.setTextSize(18);
            bodytype.setTypeface(bodytype.getTypeface(), Typeface.BOLD);
            bodytype.setText("NormalWeight");

        }
        else if (resultfb >= 25.0 && resultfb <= 29.9){
            bodytype.setVisibility(VISIBLE);
            bodytype.setTextSize(18);
            bodytype.setTypeface(bodytype.getTypeface(), Typeface.BOLD);
            bodytype.setText("OverWeight");

        }
        else if (resultfb >= 30.0){
            bodytype.setVisibility(VISIBLE);
            bodytype.setTextSize(18);
            bodytype.setTypeface(bodytype.getTypeface(), Typeface.BOLD);
            bodytype.setText("Obese");
        }
        else
        {

        }
    }

    public void calculateBMR(){
        optionsChecked();
        if(rmale.isChecked()) {
            bmrMen = 66 + (6.23 * weight) + (12.7 * hm) - (6.8 * age);
            Log.d("male", "value" + bmrMen);
            result = (Math.round(bmrMen * 100.0) / 100.0);
            resultf = (float) result;
            Log.d("male", "value" + bmrMen);
            tBMRLable.setVisibility(VISIBLE);
            tBMRLable.setTextSize(18);
            tBMRLable.setTypeface(tBMRLable.getTypeface(), Typeface.BOLD);
            tBMRResult.setVisibility(VISIBLE);
            tBMRResult.setTextSize(18);
            tBMRResult.setTypeface(tBMRResult.getTypeface(), Typeface.BOLD);

            bmrMen = Math.round(bmrMen);
            tBMRResult.setText(Double.toString(bmrMen));
            tcalories.setVisibility(VISIBLE);
            tcalories.setTextSize(18);
            tcalories.setTypeface(tcalories.getTypeface(), Typeface.BOLD);
            tcaloriesresult.setVisibility(VISIBLE);
            tcaloriesresult.setTextSize(18);
            tcaloriesresult.setTypeface(tcaloriesresult.getTypeface(), Typeface.BOLD);
            rate = Integer.parseInt(activityrate.getText().toString());
            if (rate == 1)
            {
                calorieintake = resultf * (float) 1.2;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            }
            else if(rate ==2)
            {
                calorieintake = resultf * (float) 1.375;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));

            }
            else if(rate ==3)
            {
                calorieintake = resultf * (float) 1.55;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));

            }
            else if (rate ==4)
            {
                calorieintake = resultf * (float) 1.725;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            }
            else if (rate ==5)
            {
                calorieintake = resultf * (float) 1.9;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            }
            else
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Activity Rate Error");
                alert.setMessage("Enter valid Activity Rate from 1 to 5 ...");
                alert.setPositiveButton("OK",null);
                alert.show();
            }
        }
        else if (rfemale.isChecked())
        {
            bmrWomen = 655 + (4.35 * weight) + (4.7 *hm) - (4.7 *age);
            Log.d("female","value" +bmrWomen);
            Log.d("female","valueW" +weight);
            Log.d("female","valuei" +inches);
            Log.d("female","valuea" +age);
            result = (Math.round(bmrWomen * 100.0) / 100.0);
            resultf = (float)result;
            Log.d("female","value" +bmrWomen);
            tBMRLable.setVisibility(VISIBLE);
            tBMRLable.setTextSize(18);
            tBMRLable.setTypeface(tBMRLable.getTypeface(), Typeface.BOLD);

            tBMRResult.setVisibility(VISIBLE);
            tBMRResult.setTextSize(18);
            tBMRResult.setTypeface(tBMRResult.getTypeface(), Typeface.BOLD);

            tcalories.setVisibility(VISIBLE);
            tcalories.setTextSize(18);
            tcalories.setTypeface(tcalories.getTypeface(), Typeface.BOLD);

            tcaloriesresult.setVisibility(VISIBLE);
            tcaloriesresult.setTextSize(18);
            tcaloriesresult.setTypeface(tcalories.getTypeface(), Typeface.BOLD);

            bmrWomen = Math.round(bmrWomen);
            tBMRResult.setText(Double.toString(bmrWomen));
            rate = Integer.parseInt(activityrate.getText().toString());
            if (rate == 1)
            {
                calorieintake = resultf * (float) 1.2;
                calorieintake = Math.round(calorieintake);
                Log.d("female","value" +calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            }
            else if(rate ==2)
            {
                calorieintake = resultf * (float) 1.375;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));

            }
            else if(rate ==3)
            {
                calorieintake = resultf * (float) 1.55;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));

            }
            else if (rate ==4)
            {
                calorieintake = resultf * (float) 1.725;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            }
            else if (rate ==5)
            {
                calorieintake = resultf * (float) 1.9;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0) / 100.0);
                resultf = (float)result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            }
            else
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Activity Rate Error");
                alert.setMessage("Enter valid Activity Rate from 1 to 5 ...");
                alert.setPositiveButton("OK",null);
                alert.show();
            }

        }

    }

    public void optionsChecked(){
        Log.d("inside","male"+rmale.isChecked());
        Log.d("inside","female"+rfemale.isChecked());
        if(rmale.isChecked()){
            Log.d("inside","male");
            rfemale.setChecked(false);
        }
        else
        {
            Log.d("inside","male");
            rmale.setChecked(false);

        }

    }

}
