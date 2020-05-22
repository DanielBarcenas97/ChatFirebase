package com.example.sistemadeinferenciamd.ui.main;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sistemadeinferenciamd.R;
import com.example.sistemadeinferenciamd.databinding.FragmentCompletoBinding;
import com.example.sistemadeinferenciamd.databinding.FragmentReducidoBinding;

public class Fragment2 extends Fragment {

    private FragmentReducidoBinding Binding;
    double intercept = 7.35952;
    double radius = 2.0493;
    double texture = -0.38473;
    double perimeter = 0.07151;
    double area = -0.0398;
    double smoothness = -76.43227;
    double compactness = 1.46242;
    double concavity = -8.4687;
    double concavePoints = -66.82176;
    double symmetry = -16.27824;
    double fractalDim = 68.33703;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Binding = FragmentReducidoBinding.inflate(inflater);
        View view = Binding.getRoot();
        Binding.buttonPredecir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double itexture = Double.parseDouble(Binding.texture.getText().toString());
                double iarea = Double.parseDouble(Binding.area.getText().toString());
                double icompactness = Double.parseDouble(Binding.compacidad.getText().toString());
                double iconcavity = Double.parseDouble(Binding.concavidad.getText().toString());
                double isymmetry = Double.parseDouble(Binding.simetria.getText().toString());
                double ifractalDim = Double.parseDouble(Binding.dimensionFractal.getText().toString());


                double  value = intercept + (texture * itexture) + (area * iarea) + (compactness * icompactness) + (concavity * iconcavity) + (symmetry * isymmetry) + (fractalDim * ifractalDim);
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
