package com.niderkwel.iskra_mobile.components.ui

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.niderkwel.domain.models.MapMark
import com.niderkwel.iskra_mobile.R
import com.niderkwel.iskra_mobile.theme.isLight
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.TilesOverlay
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow

private val START_POINT = GeoPoint(51.529705, 45.978542)
private val BOUNDS = listOf(
    GeoPoint(51.345952973719115, 45.80383655875106),
    GeoPoint(51.662299423887255, 46.34325486671529),
)

@Composable
fun OsmdroidMapView(
    context: Context,
    markers: List<MapMark> = emptyList()
) {
    val mapView = rememberMapViewWithLifecycle()
    Configuration.getInstance().userAgentValue =
        BuildConfig.LIBRARY_PACKAGE_NAME

    val isLight = MaterialTheme.colorScheme.isLight()

    AndroidView({ mapView }) { view ->
        view.setTileSource(TileSourceFactory.MAPNIK)
        view.setMultiTouchControls(true)
        view.setExpectedCenter(START_POINT)
        view.minZoomLevel = 15.0
        view.maxZoomLevel = 20.0
        view.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        if (!isLight)
            view.overlayManager.tilesOverlay.setColorFilter(TilesOverlay.INVERT_COLORS)

        view.controller.let {
            it.setZoom(15.0)
            it.setCenter(START_POINT)
        }

        markers.forEach { mark ->
            val marker = Marker(view).apply {
                setInfoWindow(
                    MarkerInfoWindow(
                        R.layout.mark_window,
                        view
                    )
                )
                icon = ResourcesCompat.getDrawable(context.resources, R.drawable.map_mark, null)
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                position =
                    GeoPoint(mark.coordinates[1].toDouble(), mark.coordinates[0].toDouble())
                title = mark.name
                textLabelFontSize = 18
                subDescription = mark.typeName()

                setInfoWindowAnchor(
                    Marker.ANCHOR_CENTER,
                    -0.1f
                )

                setOnMarkerClickListener { marker, _ ->
                    if (marker.isInfoWindowOpen) marker.closeInfoWindow()
                    else marker.showInfoWindow()

                    true
                }
            }

            view.overlays.add(marker)
        }

        view.invalidate()
    }
}

@Composable
private fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current

    val mapView = remember {
        MapView(context)
    }

    val observer = remember { MapViewLifecycleObserver(mapView) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(Unit) {
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    return mapView
}

class MapViewLifecycleObserver(private val mapView: MapView) : DefaultLifecycleObserver {
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        mapView.onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        mapView.onPause()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        mapView.onDetach()
    }
}