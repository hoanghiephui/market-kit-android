package io.horizontalsystems.marketkit.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FullCoin(
    val coin: Coin,
    val tokens: List<Token>
) : Parcelable {

    override fun toString(): String {
        return "FullCoin [ \n$coin, \n${tokens.joinToString(separator = ",\n")} \n]"
    }

}
