package com.fancer.mvvm.ui.fragment.ky

import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.fancer.R
import com.fancer.mvvm.ui.adapter.LocalFragmentPageAdapter
import com.fancer.mvvm.ui.fragment.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_ky_main.*


/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/28
 * @E-mail: W_SpongeBob@163.com
 * @Desc：父容器页面
 */
class KyMainFragment : BaseFragment(R.layout.fragment_ky_main) {
    override fun goProcess() {
    }


    override fun init() {
        val fragmentList: Array<Fragment> = Array(3) {
            KyMineFragment()
        }

        fragmentList[0] = KyHomeFragment()

        vp_mainContainer.adapter = LocalFragmentPageAdapter(fragmentList, parentFragmentManager)
        vp_mainContainer.offscreenPageLimit = 3

        nav_bottomView.itemIconTintList = null
//        nav_bottomView.itemIconSize = 20
        adjustNavigationIcoSize(nav_bottomView)
    }

    private fun adjustNavigationIcoSize(navigation: BottomNavigationView) {
        val menuView =
            navigation.getChildAt(0) as BottomNavigationMenuView
        for (i in 0 until menuView.childCount) {

            val item = menuView.getChildAt(i) as BottomNavigationItemView
            val iconView: ImageView = item.findViewById(R.id.icon)
            val smallLabel: TextView = item.findViewById(R.id.smallLabel)
            val largeLabel: TextView = item.findViewById(R.id.largeLabel)

            val layoutParams: FrameLayout.LayoutParams =
                iconView.layoutParams as FrameLayout.LayoutParams

            layoutParams.height = resources.getDimension(R.dimen.dp_22).toInt()
            layoutParams.width = resources.getDimension(R.dimen.dp_22).toInt()

            smallLabel.setPadding(
                smallLabel.paddingLeft,
                resources.getDimension(R.dimen.dp_25).toInt(),
                smallLabel.paddingRight,
                0
            )
            largeLabel.setPadding(
                largeLabel.paddingLeft,
                resources.getDimension(R.dimen.dp_25).toInt(),
                largeLabel.paddingRight,
                0
            )

            iconView.scaleType = ImageView.ScaleType.FIT_XY
        }

        selectItem(0)
    }

    fun selectItem(position: Int) {
        val menuView =
            nav_bottomView.getChildAt(0) as BottomNavigationMenuView

        for (i in 0 until menuView.childCount) {
            menuView.getChildAt(i).isSelected = i == position
        }
    }

    override fun initEvent() {
        vp_mainContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                nav_bottomView.selectedItemId = nav_bottomView.menu.getItem(position).itemId
                selectItem(position)
            }
        })
        nav_bottomView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_ky_home -> {
                    vp_mainContainer.setCurrentItem(0, false)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_ky_media -> {
                    vp_mainContainer.setCurrentItem(1, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_ky_follow -> {
                    vp_mainContainer.setCurrentItem(2, false)
                    return@setOnNavigationItemSelectedListener true
                }
            }

            return@setOnNavigationItemSelectedListener false

        }
    }

}