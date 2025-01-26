plugins {
    id(Plugins.LIFTING_ANDROID_LIBRARY)
    id(Plugins.LIFTING_ANDROID_LIBRARY_COMPOSE)
}
android { namespace = "com.lifting.app.core.ui" }

dependencies {
    coreDesignSystem()
    api(ThirdParty.collapsingToolbar)
    api(ThirdParty.coil)
}