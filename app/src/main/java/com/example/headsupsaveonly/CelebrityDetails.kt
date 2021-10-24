package com.example.headsupsaveonly

import com.google.gson.annotations.SerializedName

class CelebrityDetails {
    var data: ArrayList<CelebrityDetails>? = null

    class Celebrity {

        @SerializedName("name")
        var name: String? = null

        @SerializedName("taboo1")
        var taboo1: String? = null

        @SerializedName("taboo2")
        var taboo2: String? = null

        @SerializedName("taboo3")
        var taboo3: String? = null

        @SerializedName("pk")
        var pk: Int? = null

        constructor(name: String?, taboo1: String?, taboo2: String?, taboo3: String?, pk: Int?) {
            this.name = name
            this.taboo1 = taboo1
            this.taboo2 = taboo2
            this.taboo3 = taboo3
            this.pk=pk
        }
    }
}