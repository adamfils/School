package com.techguy.school.model

import com.orm.SugarRecord

class InfoModel: SugarRecord{
    var dbn: String? = null
    var school_name: String? = null
    var num_of_sat_test_takers: String? = null
    var sat_critical_reading_avg_score: String? = null
    var sat_math_avg_score: String? = null
    var sat_writing_avg_score: String? = null

    constructor(){

    }

    constructor(dbn: String?, school_name: String?, num_of_sat_test_takers: String?, sat_critical_reading_avg_score: String?, sat_math_avg_score: String?, sat_writing_avg_score: String?) {
        this.dbn = dbn
        this.school_name = school_name
        this.num_of_sat_test_takers = num_of_sat_test_takers
        this.sat_critical_reading_avg_score = sat_critical_reading_avg_score
        this.sat_math_avg_score = sat_math_avg_score
        this.sat_writing_avg_score = sat_writing_avg_score
    }


}