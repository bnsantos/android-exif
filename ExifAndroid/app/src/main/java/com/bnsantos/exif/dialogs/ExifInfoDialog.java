package com.bnsantos.exif.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bnsantos.exif.R;

import java.io.IOException;

/**
 * Created by bruno on 11/11/14.
 */
public class ExifInfoDialog extends DialogFragment {
    private ExifInterface mExif;

    public ExifInfoDialog(Uri mUri) {
        try {
            this.mExif = new ExifInterface(mUri.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.dialog_exif, null);
        setExifInfo(dialogView);
        builder.setView(dialogView)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ExifInfoDialog.this.getDialog().dismiss();
                    }
                });
        return builder.create();
    }

    private void setExifInfo(View view) {
        TextView aperture = (TextView) view.findViewById(R.id.exif1Aperture);
        aperture.setText(getString(R.string.aperture, mExif.getAttribute(ExifInterface.TAG_APERTURE)));


        TextView exposure = (TextView) view.findViewById(R.id.exif2Exposure);
        exposure.setText(getString(R.string.exposure_time, mExif.getAttribute(ExifInterface.TAG_EXPOSURE_TIME)));

        TextView flash = (TextView) view.findViewById(R.id.exif3Flash);
        flash.setText(getString(R.string.flash, mExif.getAttribute(ExifInterface.TAG_FLASH)));

        TextView focalLength = (TextView) view.findViewById(R.id.exif4FocalLength);
        focalLength.setText(getString(R.string.focal_length, mExif.getAttribute(ExifInterface.TAG_FOCAL_LENGTH)));

        TextView alt = (TextView) view.findViewById(R.id.exif5GPSAlt);
        alt.setText(getString(R.string.altitude, mExif.getAttribute(ExifInterface.TAG_GPS_ALTITUDE)));

        TextView alt_ref = (TextView) view.findViewById(R.id.exif6GPSAltRef);
        alt_ref.setText(getString(R.string.altitude_ref, mExif.getAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF)));

        TextView datestamp = (TextView) view.findViewById(R.id.exif7GPSDatestamp);
        datestamp.setText(getString(R.string.gps_datestamp, mExif.getAttribute(ExifInterface.TAG_GPS_DATESTAMP)));

        TextView lat = (TextView) view.findViewById(R.id.exif8GPSLat);
        lat.setText(getString(R.string.gps_lat, mExif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)));

        TextView lat_ref = (TextView) view.findViewById(R.id.exif9GPSLatRef);
        lat_ref.setText(getString(R.string.gps_lat_ref, mExif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF)));

        TextView lon = (TextView) view.findViewById(R.id.exif10GPSLon);
        lon.setText(getString(R.string.gps_lon, mExif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)));

        TextView lon_ref = (TextView) view.findViewById(R.id.exif11GPSLonRef);
        lon_ref.setText(getString(R.string.gps_lon_ref, mExif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF)));

        TextView method = (TextView) view.findViewById(R.id.exif12GPSProcessingMethod);
        method.setText(getString(R.string.gps_processing_method, mExif.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD)));

        TextView timestamp = (TextView) view.findViewById(R.id.exif13GPSTimestamp);
        timestamp.setText(getString(R.string.gps_timestamp, mExif.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP)));

        TextView length = (TextView) view.findViewById(R.id.exif14ImageLength);
        length.setText(getString(R.string.image_length, mExif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)));

        TextView width = (TextView) view.findViewById(R.id.exif15ImageWidth);
        width.setText(getString(R.string.image_width, mExif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)));

        TextView iso = (TextView) view.findViewById(R.id.exif16ISO);
        iso.setText(getString(R.string.iso, mExif.getAttribute(ExifInterface.TAG_ISO)));

        TextView make = (TextView) view.findViewById(R.id.exif17Make);
        make.setText(getString(R.string.make, mExif.getAttribute(ExifInterface.TAG_MAKE)));

        TextView model = (TextView) view.findViewById(R.id.exif18Model);
        model.setText(getString(R.string.model, mExif.getAttribute(ExifInterface.TAG_MODEL)));

        TextView orientation = (TextView) view.findViewById(R.id.exif19Orientation);
        orientation.setText(getString(R.string.orientation, mExif.getAttribute(ExifInterface.TAG_ORIENTATION)));

        TextView balance = (TextView) view.findViewById(R.id.exif20WhiteBalance);
        balance.setText(getString(R.string.white_balance, mExif.getAttribute(ExifInterface.TAG_WHITE_BALANCE)));

        TextView dateTime = (TextView) view.findViewById(R.id.exif21DateTime);
        dateTime.setText(getString(R.string.date_time, mExif.getAttribute(ExifInterface.TAG_DATETIME)));
    }
}
