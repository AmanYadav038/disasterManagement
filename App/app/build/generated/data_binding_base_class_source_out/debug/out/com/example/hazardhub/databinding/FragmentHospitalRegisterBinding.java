// Generated by view binder compiler. Do not edit!
package com.example.hazardhub.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.hazardhub.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHospitalRegisterBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView backHome;

  @NonNull
  public final EditText bedCount;

  @NonNull
  public final TextInputEditText editRegisterPassword;

  @NonNull
  public final TextInputEditText editTextEmail;

  @NonNull
  public final TextInputEditText editTextName;

  @NonNull
  public final TextInputEditText editTextNum;

  @NonNull
  public final EditText line1;

  @NonNull
  public final EditText line2;

  @NonNull
  public final EditText pincodeDetail;

  @NonNull
  public final Button registerBtn;

  @NonNull
  public final Spinner statesSpinner;

  @NonNull
  public final EditText time;

  private FragmentHospitalRegisterBinding(@NonNull LinearLayout rootView,
      @NonNull TextView backHome, @NonNull EditText bedCount,
      @NonNull TextInputEditText editRegisterPassword, @NonNull TextInputEditText editTextEmail,
      @NonNull TextInputEditText editTextName, @NonNull TextInputEditText editTextNum,
      @NonNull EditText line1, @NonNull EditText line2, @NonNull EditText pincodeDetail,
      @NonNull Button registerBtn, @NonNull Spinner statesSpinner, @NonNull EditText time) {
    this.rootView = rootView;
    this.backHome = backHome;
    this.bedCount = bedCount;
    this.editRegisterPassword = editRegisterPassword;
    this.editTextEmail = editTextEmail;
    this.editTextName = editTextName;
    this.editTextNum = editTextNum;
    this.line1 = line1;
    this.line2 = line2;
    this.pincodeDetail = pincodeDetail;
    this.registerBtn = registerBtn;
    this.statesSpinner = statesSpinner;
    this.time = time;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHospitalRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHospitalRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_hospital_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHospitalRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backHome;
      TextView backHome = ViewBindings.findChildViewById(rootView, id);
      if (backHome == null) {
        break missingId;
      }

      id = R.id.bedCount;
      EditText bedCount = ViewBindings.findChildViewById(rootView, id);
      if (bedCount == null) {
        break missingId;
      }

      id = R.id.editRegisterPassword;
      TextInputEditText editRegisterPassword = ViewBindings.findChildViewById(rootView, id);
      if (editRegisterPassword == null) {
        break missingId;
      }

      id = R.id.editTextEmail;
      TextInputEditText editTextEmail = ViewBindings.findChildViewById(rootView, id);
      if (editTextEmail == null) {
        break missingId;
      }

      id = R.id.editTextName;
      TextInputEditText editTextName = ViewBindings.findChildViewById(rootView, id);
      if (editTextName == null) {
        break missingId;
      }

      id = R.id.editTextNum;
      TextInputEditText editTextNum = ViewBindings.findChildViewById(rootView, id);
      if (editTextNum == null) {
        break missingId;
      }

      id = R.id.line1;
      EditText line1 = ViewBindings.findChildViewById(rootView, id);
      if (line1 == null) {
        break missingId;
      }

      id = R.id.line2;
      EditText line2 = ViewBindings.findChildViewById(rootView, id);
      if (line2 == null) {
        break missingId;
      }

      id = R.id.pincode_detail;
      EditText pincodeDetail = ViewBindings.findChildViewById(rootView, id);
      if (pincodeDetail == null) {
        break missingId;
      }

      id = R.id.registerBtn;
      Button registerBtn = ViewBindings.findChildViewById(rootView, id);
      if (registerBtn == null) {
        break missingId;
      }

      id = R.id.states_spinner;
      Spinner statesSpinner = ViewBindings.findChildViewById(rootView, id);
      if (statesSpinner == null) {
        break missingId;
      }

      id = R.id.time;
      EditText time = ViewBindings.findChildViewById(rootView, id);
      if (time == null) {
        break missingId;
      }

      return new FragmentHospitalRegisterBinding((LinearLayout) rootView, backHome, bedCount,
          editRegisterPassword, editTextEmail, editTextName, editTextNum, line1, line2,
          pincodeDetail, registerBtn, statesSpinner, time);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
