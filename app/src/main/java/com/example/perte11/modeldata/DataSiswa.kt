package com.example.perte11.modeldata

import com.google.gson.annotations.SerializedName

data class DataSiswa(
    val id: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    @SerializedName("telepon") val telpon: String = ""
)

data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false
)

data class DetailSiswa(
    val id: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = ""
)

fun DetailSiswa.toDataSiswa(): DataSiswa = DataSiswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon
)