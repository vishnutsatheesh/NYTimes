package com.newyorktimes.model

data class Response(var status: String, var copyright: String, var num_results: Int, var results: ArrayList<Result>) {
    data class Result(var url: String, var adx_keywords: String, var column: String, var section: String, var byline: String, var type: String, var title: String, var abstract: String, var published_date: String, var source: String, var id: String, var asset_id: String, var views: String, var media: List<Medium>)
    data class Medium(var type: String, var subtype: String, var caption: String, var copyright: String, var approved_for_syndication: String, var media_metadata: List<MediaMetadatum>)
    data class MediaMetadatum(var url: String, var format: String, var height: String, var width: String, var hourly_rate_id: String)
}


