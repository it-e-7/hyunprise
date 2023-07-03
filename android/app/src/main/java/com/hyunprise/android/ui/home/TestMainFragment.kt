import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.drawerlayout.widget.DrawerLayout
import com.hyunprise.android.R
import com.hyunprise.android.databinding.FragmentIssuedCouponListBinding
import com.hyunprise.android.databinding.FragmentTestMainBinding

class TestMainFragment : Fragment() {

    private var _binding: FragmentTestMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        val view = inflater.inflate(R.layout.fragment_test_main, container, false)
        _binding = FragmentTestMainBinding.inflate(inflater, container, false)
//
        val drawerLayout = binding.drawerLayoutTest
//
        // 버튼 클릭 리스너 설정
        // X 버튼 클릭 리스너 설정
        val closeButton = binding.btnCloseDrawer
        closeButton.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        val toggleButton = binding.btnToggleDrawer
        toggleButton.setOnClickListener {
            // 드로워 토글
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
