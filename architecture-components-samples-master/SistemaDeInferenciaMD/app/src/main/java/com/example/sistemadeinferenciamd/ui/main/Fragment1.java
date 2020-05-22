package com.example.sistemadeinferenciamd.ui.main;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sistemadeinferenciamd.R;
import com.example.sistemadeinferenciamd.databinding.FragmentCompletoBinding;

public class Fragment1 extends Fragment {


    private FragmentCompletoBinding Binding;
    private double intercept = 7.35952;
    private double radius = 2.0493;
    private double texture = -0.38473;
    private double perimeter = 0.07151;
    private double area = -0.0398;
    private double smoothness = -76.43227;
    private double compactness = 1.46242;
    private double concavity = -8.4687;
    private double concavePoints = -66.82176;
    private double symmetry = -16.27824;
    private double fractalDim = 68.33703;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Binding = FragmentCompletoBinding.inflate(inflater);
        View view = Binding.getRoot();


        Binding.buttonPredecir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double iradio = Double.parseDouble(Binding.radio.getText().toString());
                double itexture = Double.parseDouble(Binding.texture.getText().toString());
                double iperimeter = Double.parseDouble(Binding.perimetro.getText().toString());
                double iarea = Double.parseDouble(Binding.area.getText().toString());
                double ismoothness = Double.parseDouble(Binding.suavidad.getText().toString());
                double icompactness = Double.parseDouble(Binding.compacidad.getText().toString());
                double iconcavity = Double.parseDouble(Binding.concavidad.getText().toString());
                double iconcavePoints = Double.parseDouble(Binding.puntosConcavos.getText().toString());
                double isymmetry = Double.parseDouble(Binding.simetria.getText().toString());
                double ifractalDim = Double.parseDouble(Binding.dimensionFractal.getText().toString());

                double  value = (intercept + (radius * iradio) + (texture * itexture) + (perimeter * iperimeter) + (area * iarea) + (smoothness * ismoothness) + (compactness * icompactness) + (concavity * iconcavity) + (concavePoints * iconcavePoints) + (symmetry * isymmetry) + (fractalDim * ifractalDim));

                double prediction =  1 /(1 + Math.exp(-value));


                if(prediction > 0.5 ){
                    Binding.mensaje.setText("Tumor Benigno");
                    Binding.mensaje.setVisibility(View.VISIBLE);
                }else{
                    Binding.mensaje.setText("Tumor Maligno");
                    Binding.mensaje.setVisibility(View.VISIBLE);
                }

                Toast.makeText(getContext(), "Value = " + prediction, Toast.LENGTH_SHORT).show();

            }
        });
        return view;

    }
}
