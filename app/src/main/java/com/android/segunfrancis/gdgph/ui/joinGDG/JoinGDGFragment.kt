package com.android.segunfrancis.gdgph.ui.joinGDG

import android.animation.Animator
import android.animation.AnimatorInflater
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.segunfrancis.gdgph.R
import com.android.segunfrancis.gdgph.utility.MethodUtils.Companion.scaleAnimator
import com.google.android.material.button.MaterialButton

class JoinGDGFragment : Fragment() {

    private lateinit var joinGDGViewModel: JoinGDGViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        joinGDGViewModel =
            ViewModelProviders.of(this).get(JoinGDGViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_join_gdg, container, false)
        val meetupButton: MaterialButton = root.findViewById(R.id.button_meetup)
        val whatsappButton: MaterialButton = root.findViewById(R.id.button_whatsapp)
        val instagramButton: MaterialButton = root.findViewById(R.id.button_instagram)
        //scaleAnimator(meetupButton)

        val meetupButtonAnimation =
            AnimatorInflater.loadAnimator(context, R.animator.scale_animation)
        meetupButtonAnimation.apply {
            setTarget(meetupButton)
            start()
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    val whatsappButtonAnimation =
                        AnimatorInflater.loadAnimator(context, R.animator.scale_animation)
                    whatsappButtonAnimation.apply {
                        setTarget(whatsappButton)
                        start()
                        addListener(object : Animator.AnimatorListener {
                            override fun onAnimationRepeat(p0: Animator?) {

                            }

                            override fun onAnimationEnd(p0: Animator?) {
                                scaleAnimator(instagramButton)
                            }

                            override fun onAnimationCancel(p0: Animator?) {

                            }

                            override fun onAnimationStart(p0: Animator?) {

                            }
                        })
                    }
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationStart(p0: Animator?) {

                }
            })
        }


        val gdgUrl = "https://www.meetup.com/GDG-Port-Harcourt/"
        val whatsappUrl = "https://chat.whatsapp.com/8DxFlE14fO21PxMQcTOfoh"
        val instagramUrl = "https://instagram.com/gdgphc/"
        meetupButton.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(resources.getColor(R.color.colorPrimary))
            builder.setStartAnimations(root.context, R.anim.slide_in_right, R.anim.slide_out_left)
            builder.setExitAnimations(root.context, R.anim.slide_in_left, R.anim.slide_out_right)
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(root.context, Uri.parse(gdgUrl))
        }

        whatsappButton.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(resources.getColor(R.color.colorPrimary))
            builder.setStartAnimations(root.context, R.anim.slide_in_right, R.anim.slide_out_left)
            builder.setExitAnimations(root.context, R.anim.slide_in_left, R.anim.slide_out_right)
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(root.context, Uri.parse(whatsappUrl))
        }

        instagramButton.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(resources.getColor(R.color.colorPrimary))
            builder.setStartAnimations(root.context, R.anim.slide_in_right, R.anim.slide_out_left)
            builder.setExitAnimations(root.context, R.anim.slide_in_left, R.anim.slide_out_right)
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(root.context, Uri.parse(instagramUrl))
        }
        joinGDGViewModel.text.observe(this, Observer {

        })
        return root
    }
}