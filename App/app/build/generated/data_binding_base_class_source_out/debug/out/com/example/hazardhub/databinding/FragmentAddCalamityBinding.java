// Generated by view binder compiler. Do not edit!
package com.example.hazardhub.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.hazardhub.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAddCalamityBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final TextInputEditText addressLine1;

  @NonNull
  public final TextInputEditText addressLine2;

  @NonNull
  public final TextInputEditText affectedPeopleCount;

  @NonNull
  public final TextInputEditText calamityDescription;

  @NonNull
  public final TextInputEditText calamityTitle;

  @NonNull
  public final ImageView imagePreview;

  @NonNull
  public final EditText pincodeDetail;

  @NonNull
  public final Spinner statesSpinner;

  @NonNull
  public final Button submitProjectButton;

  @NonNull
  public final Button uploadImageButton;

  private FragmentAddCalamityBinding(@NonNull ScrollView rootView,
      @NonNull TextInputEditText addressLine1, @NonNull TextInputEditText addressLine2,
      @NonNull TextInputEditText affectedPeopleCount,
      @NonNull TextInputEditText calamityDescription, @NonNull TextInputEditText calamityTitle,
      @NonNull ImageView imagePreview, @NonNull EditText pincodeDetail,
      @NonNull Spinner statesSpinner, @NonNull Button submitProjectButton,
      @NonNull Button uploadImageButton) {
    this.rootView = rootView;
    this.addressLine1 = addressLine1;
    this.addressLine2 = addressLine2;
    this.affectedPeopleCount = affectedPeopleCount;
    this.calamityDescription = calamityDescription;
    this.calamityTitle = calamityTitle;
    this.imagePreview = imagePreview;
    this.pincodeDetail = pincodeDetail;
    this.statesSpinner = statesSpinner;
    this.submitProjectButton = submitProjectButton;
    this.uploadImageButton = uploadImageButton;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAddCalamityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAddCalamityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_add_calamity, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAddCalamityBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addressLine1;
      TextInputEditText addressLine1 = ViewBindings.findChildViewById(rootView, id);
      if (addressLine1 == null) {
        break missingId;
      }

      id = R.id.addressLine2;
      TextInputEditText addressLine2 = ViewBindings.findChildViewById(rootView, id);
      if (addressLine2 == null) {
        break missingId;
      }

      id = R.id.affectedPeopleCount;
      TextInputEditText affectedPeopleCount = ViewBindings.findChildViewById(rootView, id);
      if (affectedPeopleCount == null) {
        break missingId;
      }

      id = R.id.calamityDescription;
      TextInputEditText calamityDescription = ViewBindings.findChildViewById(rootView, id);
      if (calamityDescription == null) {
        break missingId;
      }

      id = R.id.calamityTitle;
      TextInputEditText calamityTitle = ViewBindings.findChildViewById(rootView, id);
      if (calamityTitle == null) {
        break missingId;
      }

      id = R.id.imagePreview;
      ImageView imagePreview = ViewBindings.findChildViewById(rootView, id);
      if (imagePreview == null) {
        break missingId;
      }

      id = R.id.pincode_detail;
      EditText pincodeDetail = ViewBindings.findChildViewById(rootView, id);
      if (pincodeDetail == null) {
        break missingId;
      }

      id = R.id.statesSpinner;
      Spinner statesSpinner = ViewBindings.findChildViewById(rootView, id);
      if (statesSpinner == null) {
        break missingId;
      }

      id = R.id.submitProjectButton;
      Button submitProjectButton = ViewBindings.findChildViewById(rootView, id);
      if (submitProjectButton == null) {
        break missingId;
      }

      id = R.id.uploadImageButton;
      Button uploadImageButton = ViewBindings.findChildViewById(rootView, id);
      if (uploadImageButton == null) {
        break missingId;
      }

      return new FragmentAddCalamityBinding((ScrollView) rootView, addressLine1, addressLine2,
          affectedPeopleCount, calamityDescription, calamityTitle, imagePreview, pincodeDetail,
          statesSpinner, submitProjectButton, uploadImageButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
