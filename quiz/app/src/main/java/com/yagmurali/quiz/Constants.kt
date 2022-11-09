package com.yagmurali.quiz

object Constants {

    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions() : ArrayList<Questions> {
        val queList = ArrayList<Questions>()
        val q1 = Questions(
            1,
            "Bu bayrak hangi ülkeye ait",
            R.drawable.ic_flag_of_argentina,
            "Arjantin",
            "Kuveyt",
            "Belçika",
            "Avustralya",
            1
        )
        val q2 = Questions(
            2,
            "Bu bayrak hangi ülkeye ait",
            R.drawable.ic_flag_of_australia,
            "Belçika",
            "Almanya",
            "Kuveyt",
            "Avustralya",
            4
        )
        val q3 = Questions(
            3,
            "Bu bayrak hangi ülkeye ait",
            R.drawable.ic_flag_of_belgium,
            "Hindistan",
            "Danimarka",
            "Belçika",
            "Fiji",
            3
        )
        val q4 = Questions(
            4,
            "Bu bayrak hangi ülkeye ait",
            R.drawable.ic_flag_of_brazil,
            "Kuveyt",
            "Brezilya",
            "Yeni Zelanda",
            "Arjantin",
            2
        )
        val q5 = Questions(
            5,
            "Bu bayrak hangi ülkeye ait",
            R.drawable.ic_flag_of_denmark,
            "Danimarka",
            "Almanya",
            "Fiji",
            "Hindistan",
            1
        )
        val q6 = Questions(
            6,
            "Bu bayrak hangi ülkeye ait",
            R.drawable.ic_flag_of_fiji,
            "Belçika",
            "Yeni Zelanda",
            "Fiji",
            "Avustralya",
            3
        )
        val q7 = Questions(
            7,
            "Bu bayrak hangi ülkeye ait",
            R.drawable.ic_flag_of_germany,
            "Yeni Zelanda",
            "Kuveyt",
            "Brezilya",
            "Almanya",
            4
        )
        val q8 = Questions(
            8,
            "Bu bayrak hangi ülkeye ait",
            R.drawable.ic_flag_of_india,
            "Avustralya",
            "Hindistan",
            "Almanya",
            "Danimarka",
            2
        )
        val q9 = Questions(
            9,
            "Bu bayrak hangi ülkeye ait",
            R.drawable.ic_flag_of_kuwait,
            "Kuveyt",
            "Belçika",
            "Fiji",
            "Brezilya",
            1
        )
        val q10 = Questions(
            10,
            "Bu bayrak hangi ülkeye ait",
            R.drawable.ic_flag_of_new_zealand,
            "Arjantin",
            "Yeni Zelanda",
            "Almanya",
            "Avustralya",
            2
        )
        val q11 = Questions(
            11,
            "Bu bayrak hangi ülkeye ait",
            R.drawable.flag_of_turkiye,
            "Türkiye",
            "Suudi Arabistan",
            "İran",
            "Mısır",
            1
        )
        queList.add(q1)
        queList.add(q2)
        queList.add(q3)
        queList.add(q4)
        queList.add(q5)
        queList.add(q6)
        queList.add(q7)
        queList.add(q8)
        queList.add(q9)
        queList.add(q10)
        queList.add(q11)
        return queList
    }
}