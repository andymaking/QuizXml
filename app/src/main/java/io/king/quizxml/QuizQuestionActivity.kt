package io.king.quizxml

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import io.king.quizxml.databinding.ActivityQuizQuestionBinding
import kotlinx.android.synthetic.main.activity_quiz_question.*

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList : ArrayList<Question>? = null
    private var mSelectedQuestionPosition : Int = 0
    private var mCorrectAnswer: Int = 0
    private var mUsername: String? = null


    private val binding: ActivityQuizQuestionBinding by lazy {
        ActivityQuizQuestionBinding.inflate(layoutInflater)
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mUsername = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestions()

        setQuestion()

        optOne.setOnClickListener(this)
        optTwo.setOnClickListener(this)
        optThree.setOnClickListener(this)
        optFour.setOnClickListener(this)
        submit.setOnClickListener(this)

    }

    private fun setQuestion(){
        val question = mQuestionsList!![mCurrentPosition-1]

        defaultOptionView()

        if (mCurrentPosition == mQuestionsList!!.size){
            submit.text ="FINISH"
        }else{
            submit.text ="SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        ivProgress.text = "$mCurrentPosition / ${progressBar.max}"
        ivQuestion.text = question!!.question
        ivImage.setImageResource(question.image)
        optOne.text = question.optionOne
        optTwo.text = question.optionTwo
        optThree.text = question.optionThree
        optFour.text = question.optionFour
    }

    private fun defaultOptionView(){
        val option = ArrayList<TextView>()
        option.add(0, optOne)
        option.add(1, optTwo)
        option.add(2, optThree)
        option.add(3, optFour)

        for (option in option){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.optOne -> {selectedOptionView(optOne,1)}
            R.id.optTwo -> {selectedOptionView(optTwo,2)}
            R.id.optThree -> {selectedOptionView(optThree,3)}
            R.id.optFour -> {selectedOptionView(optFour,4)}
            R.id.submit -> {
                if (mSelectedQuestionPosition==0){
                    mCurrentPosition++

                    when{mCurrentPosition <= mQuestionsList!!.size ->{
                        setQuestion()
                        }else ->{
                            val intent =Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUsername)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswer)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                        }
                    }
                }else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedQuestionPosition){
                        answerView(mSelectedQuestionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswer++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size){
                        submit.text = "FINISH"
                    }else{
                        submit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedQuestionPosition = 0
                }
            }
        }
    }

    fun answerView(answer : Int, drawableView: Int){
        when(answer){
            1 -> {
                optOne.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            2 -> {
                optTwo.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            3 -> {
                optThree.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            4 -> {
                optFour.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionView()
        mSelectedQuestionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }
}