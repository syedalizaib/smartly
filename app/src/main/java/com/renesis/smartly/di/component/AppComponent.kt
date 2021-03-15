package com.renesis.smartly.di.component

import com.renesis.smartly.di.module.AppModule
import com.renesis.smartly.di.module.RetrofitModule
import com.renesis.smartly.ui.BaseFragment
import com.renesis.smartly.ui.MainActivity
import com.renesis.smartly.ui.home.CategoriesAdapter
import com.renesis.smartly.ui.login.LoginFragment
import com.renesis.smartly.ui.quickmode.QuickModeFragment
import com.renesis.smartly.ui.quizPlay.QuizPlayFragment
import com.renesis.smartly.ui.splash.SplashFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class])
interface AppComponent {
    fun inject(splashFragment: SplashFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(baseFragment: BaseFragment)
    fun inject(quizPlayFragment: QuizPlayFragment)
    fun inject(quickModeFragment: QuickModeFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(categoriesAdapter: CategoriesAdapter)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun appModule(appModule: AppModule): Builder
        fun retrofitModule(retrofitModule: RetrofitModule): Builder
    }
}
