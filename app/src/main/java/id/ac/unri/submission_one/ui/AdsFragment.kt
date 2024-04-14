package id.ac.unri.submission_one.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import id.ac.unri.submission_one.R
import id.ac.unri.submission_one.databinding.FragmentAdsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdsFragment : Fragment() {

    private var interstitialAd: InterstitialAd? = null

    private var _binding : FragmentAdsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MobileAds.initialize(requireContext()) {}

        val adRequest = AdRequest.Builder().build()
        val adView = binding.adView
        adView.loadAd(adRequest)

        loadInterstitialAd()

        //aksi saat button di klik
        val showInterstitialButton = binding.showInterstitialButton
        showInterstitialButton.setOnClickListener {
            showInterstitialAd()
        }

    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(), resources.getString(R.string.interstitialId), adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    interstitialAd = null
                }

                override fun onAdLoaded(view: InterstitialAd) {
                    interstitialAd = view
                }
            })
    }

    private fun showInterstitialAd(){
        if (interstitialAd != null) {
            interstitialAd?.show(requireActivity())
        }
    }

}