package fr.doranco.flash.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fr.doranco.flash.R;
import fr.doranco.flash.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private String resultat;
    private String vide;
    private boolean isComa;
    private boolean isComa2;
    private String performOperation;
    private boolean isOperation;
    private String currentOperation;
    private Double number1;
    private Double number2;

    private void clearDisplay() {
        resultat = "";
        resultat2 = "";
        vide = "";
        isComa = false;
        isComa2 = false;
        isOperation = false;
        currentOperation = "";
        number1 = 0d;
        number2 = 0d;
        binding.textViewResult.setText("");

    }

    private String resultat2 = "";


    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.button0.setOnClickListener(View -> {
            onClickButtonNumber("0");
        });
        binding.button1.setOnClickListener(View -> {
            onClickButtonNumber("1");
        });
        binding.button2.setOnClickListener(View -> {
            onClickButtonNumber("2");
        });
        binding.button3.setOnClickListener(View -> {
            onClickButtonNumber("3");
        });
        binding.button4.setOnClickListener(View -> {
            onClickButtonNumber("4");
        });
        binding.button5.setOnClickListener(View -> {
            onClickButtonNumber("5");
        });
        binding.button6.setOnClickListener(View -> {
            onClickButtonNumber("6");
        });
        binding.button7.setOnClickListener(View -> {
            onClickButtonNumber("7");
        });
        binding.button8.setOnClickListener(View -> {
            onClickButtonNumber("8");
        });
        binding.button9.setOnClickListener(View -> {
            onClickButtonNumber("9");
        });
        binding.buttonAdd.setOnClickListener(View -> {
            performOperation("+");
        });
        binding.buttonDecimal.setOnClickListener(View -> {
            setComa();
        });
        binding.buttonDivide.setOnClickListener(View -> {

            performOperation("/");
        });
        binding.buttonEquals.setOnClickListener(View -> {
            calculateResultat();
        });
        binding.buttonMultiply.setOnClickListener(View -> {
            performOperation("x");
        });
        binding.buttonSubtract.setOnClickListener(View -> {
            performOperation("-");
        });
        binding.buttonErase.setOnClickListener(View -> {
            clearDisplay();
        });
        //initialiser les variable et nettoie la vue
        clearDisplay();

        return binding.getRoot();
    }

    private void setComa() {
        if (isOperation) {
            if (isComa2) return;
            if (resultat2.length() == 0) {
                resultat2 = "0.";
                refreshView();
                return;
            }
            ;
            if (resultat2.length() > 8) {
                isComa2 = true;
                return;
            }
            isComa2 = true;
            resultat2 += ".";
            refreshView();
        } else {
            if (isComa) return;
            if (resultat.length() == 0) {
                resultat = "0.";
                refreshView();
                return;
            }
            ;
            if (resultat.length() > 8) {
                isComa = true;
                return;
            }
            isComa = true;
            resultat += ".";
            refreshView();
        }

    }

    public void onClickButtonNumber(String value) {
        vide = "";
        try {
            if (isOperation) {

                if (resultat2.length() >= 14) return;
                if ((resultat2.length() == 0) && (value.equals("0"))) return;
                resultat2 += value;

            } else {
                if (resultat.length() >= 14) return;
                if ((resultat.length() == 0) && (value.equals("0"))) return;
                resultat += value;

            }
            refreshView();
        } catch (Exception ex) {
            clearDisplay();
            Toast.makeText(getContext(), "erreur fatale" + ex.getMessage(), Toast.LENGTH_LONG);
        }


    }


    private void performOperation(String value) {
        if (!vide.isEmpty()) {
            resultat = vide;
            vide = "";
            isOperation = true;
            currentOperation = value;
            refreshView();

        }
        if (isOperation) {
            calculateResultat();
            refreshView();
            // refreshView(resultat+value);
        }
        ;
        if (resultat.isEmpty()) return;
        try {
            number1 = Double.parseDouble(resultat);
            Log.i("test2", number1.toString());
            isOperation = true;
            currentOperation = value;
            refreshView();
        } catch (Exception ex) {
            Log.i("test", ex.getMessage());
            clearDisplay();
            Toast.makeText(getContext(), "erreur fatale" + ex.getMessage(), Toast.LENGTH_LONG);
        }

    }

    private void calculateResultat() {
        Double result = 0d;
        if (!isOperation) return;
        if (resultat2.isEmpty()) return;
        try {
            number2 = Double.parseDouble(resultat2);
            switch (currentOperation) {
                case "+":
                    result = number1 + number2;

                    break;
                case "-":
                    result = number1 - number2;

                    break;
                case "x":
                    result = number1 * number2;

                    break;
                case "/":
                    if (number2 == 0) throw new Exception("division par0 resultat infini");
                    result = number1 / number2;

                    break;

                default:

            }
            clearDisplay();

            vide = result.toString();
            refreshView();

            isComa = true;


        } catch (Exception ex) {
            clearDisplay();
            Toast.makeText(getContext(), "erreur fatale" + ex.getMessage(), Toast.LENGTH_LONG);
        }
    }

    private void refreshView() {
        binding.textViewResult.setText(vide + resultat + currentOperation + resultat2);

    }
}