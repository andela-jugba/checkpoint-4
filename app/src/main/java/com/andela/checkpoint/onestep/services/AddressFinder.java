package com.andela.checkpoint.onestep.services;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by andela-jugba on 11/8/15.
 */
public class AddressFinder {
    private static final String TAG = AddressFinder.class.getSimpleName();
    private final String SERVICE_NOT_AVAILABLE = "Service not available";
    private final String INVALID_LATITUDE = "Invalid latitude long value used";
    private final String NO_ADDRESS = "No Address Found";
    private final String UNKNOWN = "UNKNOWN LOCATION";
    private Geocoder mGeocoder;
    public AddressFinder(Geocoder geocoder) {
        this.mGeocoder = geocoder;
    }

    public String findLocationAddress(Location location) {
        String errorMessage = "";
        List<Address> addresses = null;

        try {
            addresses = mGeocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (IOException ioException) {
            errorMessage = SERVICE_NOT_AVAILABLE;
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            errorMessage = INVALID_LATITUDE;
            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " + location.getLongitude(), illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = NO_ADDRESS;
                Log.e(TAG, errorMessage);
            }
            return UNKNOWN;
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            addressFragments.add(address.getCountryName());
            return TextUtils.join(System.getProperty("line.separator"), addressFragments);
        }
    }
}
