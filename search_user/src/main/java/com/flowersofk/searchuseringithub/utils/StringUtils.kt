package com.flowersofk.searchuseringithub.utils

import java.text.DecimalFormat

/**
 * String 관련 Utils
 */
class StringUtils {

    companion object {

        // 포맷 변경 [###,###]
        fun getNumberFormat(amount: Int): String {
            return DecimalFormat("###,###").format(amount)

        }

    }

}

