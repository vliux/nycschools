package org.vliux.nycschools.data

data class HighSchoolSAT(
    val dbn: String,
    val satReadingScore: Int?,
    val satMathScore: Int?,
    val satWritingScore: Int?
)
