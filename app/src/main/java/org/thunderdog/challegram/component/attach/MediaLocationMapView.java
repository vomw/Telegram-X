/*
 * This file is a part of Telegram X
 * Copyright © 2014 (tgx-android@pm.me)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * File created on 21/10/2016
 */
package org.thunderdog.challegram.component.attach;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.navigation.HeaderView;
import org.thunderdog.challegram.navigation.ViewController;
import org.thunderdog.challegram.theme.ColorId;
import org.thunderdog.challegram.theme.Theme;
import org.thunderdog.challegram.theme.ThemeId;
import org.thunderdog.challegram.tool.Screen;
import org.thunderdog.challegram.widget.CircleButton;
import org.thunderdog.challegram.widget.ShadowView;

import me.vkryl.android.widget.FrameLayoutFix;

public class MediaLocationMapView extends FrameLayoutFix implements View.OnClickListener {
  public interface Callback {
    void onLocationUpdate (Location location, boolean custom, boolean gpsLocated, boolean preventRequest, boolean isSmallZoom);
    void onForcedLocationReset ();
  }

  // Data
  private Callback callback;
  private Location currentLocation;

  // Children
  private ImageView pinView;
  private ImageView pinXView;
  private CircleButton myLocationButton;

  public static int getMapHeight (boolean big) {
    int defaultSize = Screen.dp(150f);
    return big ? Math.max(Screen.smallestSide() - HeaderView.getSize(false) - Screen.dp(60f), defaultSize) : defaultSize;
  }

  public MediaLocationMapView (Context context) {
    super(context);
  }

  public void init (ViewController<?> themeProvider, MediaLocationPointView pointView, boolean big) {
    int mapHeight = getMapHeight(big);

    // Map stub (empty view)
    View mapStub = new View(getContext());
    mapStub.setBackgroundColor(Theme.placeholderColor());
    mapStub.setLayoutParams(FrameLayoutFix.newParams(ViewGroup.LayoutParams.MATCH_PARENT, mapHeight));
    addView(mapStub);

    pinXView = new ImageView(getContext());
    pinXView.setScaleType(ImageView.ScaleType.CENTER);
    pinXView.setImageResource(R.drawable.baseline_close_18);
    pinXView.setColorFilter(Theme.getColor(ColorId.icon, ThemeId.BLUE));
    pinXView.setLayoutParams(FrameLayoutFix.newParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
    addView(pinXView);

    FrameLayoutFix.LayoutParams params = FrameLayoutFix.newParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    params.bottomMargin = Screen.dp(18f);
    pinView = new ImageView(getContext());
    pinView.setImageResource(R.drawable.ic_map_pin_44);
    pinView.setLayoutParams(params);
    addView(pinView);

    // My button
    int padding = Screen.dp(4f);
    params = FrameLayoutFix.newParams(Screen.dp(40f) + padding * 2, Screen.dp(40f) + padding * 2, Gravity.RIGHT | Gravity.BOTTOM);
    params.bottomMargin = Screen.dp(16f) - padding;
    params.rightMargin = Screen.dp(16f) - padding;

    myLocationButton = new CircleButton(getContext());
    themeProvider.addThemeInvalidateListener(myLocationButton);
    myLocationButton.init(R.drawable.baseline_gps_fixed_24, 40f, 4f, ColorId.circleButtonOverlay, ColorId.circleButtonOverlayIcon);
    myLocationButton.setId(R.id.btn_gps);
    myLocationButton.setOnClickListener(this);
    myLocationButton.setLayoutParams(params);
    addView(myLocationButton);

    // Shadow
    ShadowView shadowView = new ShadowView(getContext());
    shadowView.setSimpleTopShadow(true);
    params = FrameLayoutFix.newParams(shadowView.getLayoutParams());
    params.gravity = Gravity.BOTTOM;
    shadowView.setLayoutParams(params);
    themeProvider.addThemeInvalidateListener(shadowView);
    addView(shadowView);

    setBackgroundColor(Theme.placeholderColor());
    themeProvider.addThemeBackgroundColorListener(this, ColorId.placeholder);
    setLayoutParams(FrameLayoutFix.newParams(ViewGroup.LayoutParams.MATCH_PARENT, mapHeight, Gravity.TOP));
  }

  @Override
  public void onClick (View v) {
    // gps button clicked, logic handled in controller
  }

  public void setCallback (Callback callback) {
    this.callback = callback;
  }

  public void setMarkerPosition (double lat, double lng) {
    Location location = new Location("network");
    location.setLatitude(lat);
    location.setLongitude(lng);
    currentLocation = location;
    if (callback != null) {
      callback.onLocationUpdate(location, true, false, false, false);
    }
  }

  public void onPauseMap () { }
  public void onResumeMap () { }
  public void onDestroyMap () { }
  public void onResolutionComplete (boolean isOk) { }
  public void onLocationPermissionOk () { }
  public void checkLocationSettings (boolean requestedByUser, boolean disablePrompts) { }

  public Location getCurrentLocation () {
    return currentLocation;
  }
}
