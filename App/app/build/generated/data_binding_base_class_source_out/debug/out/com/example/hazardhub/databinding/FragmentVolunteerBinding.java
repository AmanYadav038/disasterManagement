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

public final class FragmentVolunteerBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView backHome;

  @NonNull
  public final Spinner bloodSpinner;

  @NonNull
  public final TextInputEditText editName;

  @NonNull
  public final TextInputEditText editRegisterPassword;

  @NonNull
  public final TextInputEditText editTextEmail;

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

  private FragmentVolunteerBinding(@NonNull LinearLayout rootView, @NonNull TextView backHome,
      @NonNull Spinner bloodSpinner, @NonNull TextInputEditText editName,
      @NonNull TextInputEditText editRegisterPassword, @NonNull TextInputEditText editTextEmail,
      @NonNull TextInputEditText editTextNum, @NonNull EditText line1, @NonNull EditText line2,
      @NonNull EditText pincodeDetail, @NonNull Button registerBtn,
      @NonNull Spinner statesSpinner) {
    this.rootView = rootView;
    this.backHome = backHome;
    this.bloodSpinner = bloodSpinner;
    this.editName = editName;
    this.editRegisterPassword = editRegisterPassword;
    this.editTextEmail = editTextEmail;
    this.editTextNum = editTextNum;
    this.line1 = line1;
    this.line2 = line2;
    this.pincodeDetail = pincodeDetail;
    this.registerBtn = registerBtn;
    this.statesSpinner = statesSpinner;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentVolunteerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentVolunteerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_volunteer, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentVolunteerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backHome;
      TextView backHome = ViewBindings.findChildViewById(rootView, id);
      if (backHome == null) {
        break missingId;
      }

      id = R.id.blood_spinner;
      Spinner bloodSpinner = ViewBindings.findChildViewById(rootView, id);
      if (bloodSpinner == null) {
        break missingId;
      }

      id = R.id.editName;
      TextInputEditText editName = ViewBindings.findChildViewById(rootView, id);
      if (editName == null) {
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

      return new FragmentVolunteerBinding((LinearLayout) rootView, backHome, bloodSpinner, editName,
          editRegisterPassword, editTextEmail, editTextNum, line1, line2, pincodeDetail,
          registerBtn, statesSpinner);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
