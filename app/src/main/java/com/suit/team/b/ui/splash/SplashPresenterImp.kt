package com.suit.team.b.ui.splash

import com.suit.team.b.data.local.SharedPref

class SplashPresenterImp(private val view: SplashView): SplashPresenter{
    override fun checkIsLogin() {
        if(SharedPref.isLogin == true)
            view.onLogged()
        else
            view.unLogged()
    }
}
