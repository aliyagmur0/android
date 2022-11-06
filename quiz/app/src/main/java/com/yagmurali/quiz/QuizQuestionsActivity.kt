package com.yagmurali.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var userName: String? = null
    private var mCorrectAnswers: Int = 0

    private var mCurrentPosition: Int = 1
    private var mQuestionList:ArrayList<Questions> ? = null
    private var mSelectedOptionPosition:Int = 0

    private var progressBar:ProgressBar? = null
    private var tvProgress:TextView? = null
    private var tvQuestions:TextView? = null
    private var ivImage: ImageView? = null

    private var tvOptionOne:TextView? = null
    private var tvOptionTwo:TextView? = null
    private var tvOptionThree:TextView? = null
    private var tvOptionFour:TextView? = null

    private var btnSubmit:Button? = null
    private var boolNext:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        userName = intent.getStringExtra(Constants.USER_NAME)

        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tvProgress)
        tvQuestions = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)

        btnSubmit = findViewById(R.id.btn_submit)

        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        mCurrentPosition = 1

        mQuestionList = Constants.getQuestions()

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)

        setQuestion()
    }

    private fun setQuestion() {

        defaultOptionsView()
        val q: Questions = mQuestionList!![mCurrentPosition - 1]
        ivImage?.setImageResource(q.image)
        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition / ${mQuestionList?.size}"
        tvQuestions?.text = q.questions
        tvOptionOne?.text = q.optionOne
        tvOptionTwo?.text = q.optionTwo
        tvOptionThree?.text = q.optionThree
        tvOptionFour?.text = q.optionFour

        if (mCurrentPosition == mQuestionList!!.size) {
            btnSubmit?.text = "Bitir"
        } else {
            btnSubmit?.text = "Onayla"
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        tvOptionOne?.let {
            options.add(0, it)
        }
        tvOptionTwo?.let {
            options.add(1, it)
        }
        tvOptionThree?.let {
            options.add(2, it)
        }
        tvOptionFour?.let {
            options.add(3, it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.select_option_border
        )
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_option_one -> {
                tvOptionOne?.let {
                    selectedOptionView(it,1)
                }
            }
            R.id.tv_option_two -> {
                tvOptionTwo?.let {
                    selectedOptionView(it,2)
                }
            }
            R.id.tv_option_three -> {
                tvOptionThree?.let {
                    selectedOptionView(it,3)
                }
            }
            R.id.tv_option_four -> {
                tvOptionFour?.let {
                    selectedOptionView(it,4)
                }
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++
                    when {
                        mCurrentPosition <= mQuestionList!!.size -> {
                            if (boolNext) setQuestion()
                            boolNext=false
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, userName)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList?.size)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionList?.get(mCurrentPosition-1)
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionList!!.size) {
                        btnSubmit?.text = "BITIR"
                    } else {
                        boolNext = true
                        btnSubmit?.text = "SONRAKI"
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer : Int, drawableView : Int) {
        when(answer ) {
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }
}