package com.sachith.wordwise.api.dto

import com.google.gson.annotations.SerializedName


data class Phonetics (

  @SerializedName("text"     ) var text     : String?  = null,
  @SerializedName("audio"     ) var audio     : String?  = null,
  @SerializedName("sourceUrl" ) var sourceUrl : String?  = null,
  @SerializedName("license"   ) var license   : License? = License()

)