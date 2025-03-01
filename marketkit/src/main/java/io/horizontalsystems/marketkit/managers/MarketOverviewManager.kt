package io.horizontalsystems.marketkit.managers

import io.horizontalsystems.marketkit.models.HsTimePeriod
import io.horizontalsystems.marketkit.models.MarketOverview
import io.horizontalsystems.marketkit.models.MarketOverviewResponse
import io.horizontalsystems.marketkit.providers.HsProvider
import io.reactivex.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MarketOverviewManager(
    private val nftManager: NftManager,
    private val hsProvider: HsProvider
) {

    private fun marketOverview(response: MarketOverviewResponse): MarketOverview =
        MarketOverview(
            globalMarketPoints = response.globalMarketPoints,
            coinCategories = response.coinCategories,
            topPlatforms = response.topPlatforms.map { it.topPlatform },
            nftCollections = mapOf(
                HsTimePeriod.Day1 to nftManager.topCollections(response.nft.one_day),
                HsTimePeriod.Week1 to nftManager.topCollections(response.nft.seven_day),
                HsTimePeriod.Month1 to nftManager.topCollections(response.nft.thirty_day)
            )
        )

    fun marketOverviewSingle(currencyCode: String): Single<MarketOverview> =
        hsProvider.marketOverviewSingle(currencyCode).map { marketOverview(it) }

    fun marketOverviewFlow(currencyCode: String): Flow<MarketOverview> =
        flow {
            emitAll(hsProvider.marketOverviewFlow(currencyCode).map { marketOverview(it) })
        }


}
