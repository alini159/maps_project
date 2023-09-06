package com.example.maps_project.presenter.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.maps_project.R;
import com.example.maps_project.data.room.RoomDatabase;
import com.example.maps_project.domain.Cep;
import com.example.maps_project.presenter.factory.ViewModelFactory;
import com.example.maps_project.presenter.viewModel.CepViewModel;
import com.example.maps_project.utils.Result;

public class SearchFragment extends Fragment {

    private TextView tvCep;
    private TextView tvEstado;
    private TextView tvCidade;
    private TextView tvBairro;
    private TextView tvEndereco;

    private EditText etCEP;
    private Button btnBuscarPorCEP;
    private ProgressBar progressBar;
    private CardView cardView;

    private CepViewModel viewModel;
    private RoomDatabase room;

    public SearchFragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        room = Room.databaseBuilder(getContext(), RoomDatabase.class, "CEPDataBase").allowMainThreadQueries().build();
        viewModel = new ViewModelProvider(this, new ViewModelFactory(room)).get(CepViewModel.class);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        etCEP = view.findViewById(R.id.et_cep);
        btnBuscarPorCEP = view.findViewById(R.id.btn_buscar_por_cep);
        progressBar = view.findViewById(R.id.progress_bar);
        cardView = view.findViewById(R.id.card_view);

        tvCep = view.findViewById(R.id.cepTextView);
        tvEstado = view.findViewById(R.id.estadoTextView);
        tvCidade = view.findViewById(R.id.cidadeTextView);
        tvBairro = view.findViewById(R.id.bairroTextView);
        tvEndereco = view.findViewById(R.id.enderecoTextView);

        progressBar.setVisibility(View.INVISIBLE);
        cardView.setVisibility(View.INVISIBLE);

        btnBuscarPorCEP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String cep = etCEP.getText().toString();

                if (!cep.isEmpty()) {
                    viewModel.getData(getContext(), cep).observe(getViewLifecycleOwner(), result -> {
                        switch (result.getClass().getSimpleName()) {
                            case "Success":
                                handleSuccessState((Result.Success) result);
                                break;
                            case "Error":
                                handleErrorState((Result.Error) result);
                                break;
                            case "Loading":
                                handleLoadingState();
                                break;
                        }
                    });
                } else {
                    toast("CEP vazio!");
                }
            }
        });
    }


    private void setView(Cep result) {
        this.tvCep.setText(result.getCep());
        this.tvEstado.setText(result.getEstado());
        this.tvCidade.setText(result.getCidade());
        this.tvBairro.setText(result.getBairro());
        this.tvEndereco.setText(result.getEndereco());
    }

    private void handleSuccessState(Result.Success successResult) {
        Cep data = successResult.getData();
        setView(data);
        viewModel.persistData(data);
        cardView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void handleErrorState(Result.Error errorResult) {
        String errorMessage = errorResult.getMessage();
        toast(errorMessage);
        cardView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void handleLoadingState() {
        cardView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void toast(String msg) {
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
