package com.example.vk_test

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductClass(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
) : Parcelable {}