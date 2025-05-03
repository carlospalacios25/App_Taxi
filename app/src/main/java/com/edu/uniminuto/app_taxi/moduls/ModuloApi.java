package com.edu.uniminuto.app_taxi.moduls;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.uniminuto.app_taxi.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ModuloApi extends AppCompatActivity {

    private static final String NAMESPACE = "http://regcheck.org.uk";
    private static final String METHOD_NAME = "CheckColombia";
    private static final String SOAP_ACTION = "http://regcheck.org.uk/CheckColombia";
    private static final String URL = "https://www.placaapi.co/api/reg.asmx";
    private static final String USERNAME = "carlostop";

    private EditText editTextRegistrationNumber;
    private Button buttonCheck;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api);

        referencia();

        buttonCheck.setOnClickListener(this::checkVehicle);
    }

    private class VehicleCheckTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String registrationNumber = params[0];
            String result = "";

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("RegistrationNumber", registrationNumber);
                request.addProperty("username", USERNAME);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = true;

                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                transport.call(SOAP_ACTION, envelope);

                if (envelope.getResponse() == null) {
                    return "Error: No se recibió respuesta de la API";
                }

                SoapObject response = (SoapObject) envelope.getResponse();

                if (!response.hasProperty("vehicleData")) {
                    return "Error: No se encontraron datos del vehículo (vehicleData no presente)";
                }

                SoapObject vehicleData = (SoapObject) response.getProperty("vehicleData");
                StringBuilder formattedResult = new StringBuilder();

                formattedResult.append("Marca: ").append(getPropertySafe(vehicleData, "CarMake", "CurrentTextValue")).append("\n");
                formattedResult.append("Modelo: ").append(getPropertySafe(vehicleData, "CarModel")).append("\n");
                formattedResult.append("Año de registro: ").append(getPropertySafe(vehicleData, "RegistrationYear")).append("\n");
                formattedResult.append("Tipo de combustible: ").append(getPropertySafe(vehicleData, "FuelType", "CurrentTextValue")).append("\n");
                formattedResult.append("Número de puertas: ").append(getPropertySafe(vehicleData, "NumberOfDoors", "CurrentTextValue")).append("\n");

                result = formattedResult.toString();

            } catch (IOException e) {
                e.printStackTrace();
                result = "Error de conexión: " + e.getMessage();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                result = "Error de parseo XML: " + e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                result = "Error general: " + e.getMessage();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            textViewResult.setText(result);
            Toast.makeText(ModuloApi.this, "Consulta completada", Toast.LENGTH_SHORT).show();
        }
    }

    private String getPropertySafe(SoapObject soapObject, String propertyName) {
        try {
            if (soapObject.hasProperty(propertyName)) {
                return soapObject.getProperty(propertyName).toString();
            }
            return "No disponible";
        } catch (Exception e) {
            return "No disponible";
        }
    }

    private String getPropertySafe(SoapObject soapObject, String propertyName, String nestedProperty) {
        try {
            if (soapObject.hasProperty(propertyName)) {
                SoapObject nestedObject = (SoapObject) soapObject.getProperty(propertyName);
                if (nestedObject.hasProperty(nestedProperty)) {
                    return nestedObject.getProperty(nestedProperty).toString();
                }
            }
            return "No disponible";
        } catch (Exception e) {
            return "No disponible";
        }
    }

    private void referencia() {
        editTextRegistrationNumber = findViewById(R.id.etPlacaApi);
        buttonCheck = findViewById(R.id.btnConsultara);
        textViewResult = findViewById(R.id.textViewResult);
    }

    private void checkVehicle(android.view.View v) {
        String registration = editTextRegistrationNumber.getText().toString().trim();
        if (!registration.isEmpty()) {
            new VehicleCheckTask().execute(registration);
        } else {
            Toast.makeText(ModuloApi.this, "Por favor ingrese un número de placa", Toast.LENGTH_SHORT).show();
        }
    }
}