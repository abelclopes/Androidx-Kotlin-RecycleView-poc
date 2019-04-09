package eti.com.abellopes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eti.com.abellopes.ui.fragment.marvel.MarvelFragment

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_marvel)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.ListConstraintLayout, MarvelFragment.newInstance())
                .commitNow()
        }
    }

}
