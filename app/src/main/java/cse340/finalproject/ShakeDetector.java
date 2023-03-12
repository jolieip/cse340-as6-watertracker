package cse340.finalproject;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.FloatMath;
import android.util.Log;

/**

 The ShakeDetector class detects device shakes by listening to the sensor events and
 checking if the acceleration force exceeds the predefined shake threshold. It then triggers
 a callback to notify the listener that a shake has occurred.
 */
public class ShakeDetector implements SensorEventListener {
    // The minimum acceleration force required to consider a movement as a shake
    private static final float SHAKE_THRESHOLD_GRAVITY = 1.8F;
    // The minimum time between two shake events
    private static final int SHAKE_SLOP_TIME_MS = 500;
    // The time after which the shake count is reset
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private OnShakeListener mListener;
    private long mShakeTimestamp;

    /**
     * Set a listener to be notified when a shake occurs.
     *
     * @param listener the listener to be notified
     */
    public void setOnShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }

    /**
     * The listener interface for receiving shake events.
     */
    public interface OnShakeListener {
        /**
         * Called when a shake event occurs.
         */
        public void onShake();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // ignore
    }

    /**
     * Called when a sensor event occurs.
     * Detects whether the device has been shaken and notifies the listener if so.
     *
     * @param event the sensor event that triggered the method call
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (mListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calculate the acceleration force normalized to the Earth's gravity
            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            // Calculate the total acceleration force
            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);


            // Check if the acceleration force exceeds the predefined shake threshold
            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                final long now = System.currentTimeMillis();

                // Check if enough time has passed since the last shake event
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return;
                }

                // Reset the shake count if too much time has passed
                if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    mShakeTimestamp = 0;
                }

                mShakeTimestamp = now;

                // Notify the listener that a shake event has occurred
                mListener.onShake();
            }
        }
    }
}
