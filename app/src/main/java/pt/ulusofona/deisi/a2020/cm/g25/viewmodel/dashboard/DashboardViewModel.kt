package pt.ulusofona.deisi.a2020.cm.g25.viewmodel.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.maps.LocationSource

class DashboardViewModel(application: Application) : AndroidViewModel(application)  {

    private var listener: LocationSource.OnLocationChangedListener? = null

    fun registerListener(OnLocationChangedListener: LocationSource.OnLocationChangedListener) {
        listener = OnLocationChangedListener
    }

    fun loadData(){

    }
}
