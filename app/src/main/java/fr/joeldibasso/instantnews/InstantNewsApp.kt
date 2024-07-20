package fr.joeldibasso.instantnews

import android.app.Application
import fr.joeldibasso.instantnews.api.NewsClient

class InstantNewsApp: Application() {
    companion object {
         val newsClient: NewsClient by lazy {
             NewsClient.create()
         }
    }
}