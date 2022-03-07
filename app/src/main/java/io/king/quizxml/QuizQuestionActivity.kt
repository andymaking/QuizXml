package io.king.quizxml

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import io.king.quizxml.databinding.ActivityQuizQuestionBinding

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList : ArrayList<Question>? = null
    private var mSelectedQuestionPosition : Int = 0

    private val progressBar = findViewById<ProgressBar>(R.id.progressBar)
    private val ivProgress = findViewById<TextView>(R.id.ivProgress)
    private val ivQuestion= findViewById<TextView>(R.id.ivQuestion)
    private val ivImage= findViewById<ImageView>(R.id.ivImage)
    private val optOne= findViewById<TextView>(R.id.optOne)
    private val optTwo= findViewById<TextView>(R.id.optTwo)
    private val optThree= findViewById<TextView>(R.id.optThree)
    private val optFour= findViewById<TextView>(R.id.optFour)
    private val submit= findViewById<Button>(R.id.submit)

    private val binding: ActivityQuizQuestionBinding by lazy {
        ActivityQuizQuestionBinding.inflate(layoutInflater)
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mQuestionsList = Constants.getQuestions()

        setQuestion()

        optOne.setOnClickListener(this)
        optTwo.setOnClickListener(this)
        optThree.setOnClickListener(this)
        optFour.setOnClickListener(this)

    }

    private fun setQuestion(){
        mCurrentPosition =1
        val question = mQuestionsList!![mCurrentPosition-1]

        defaultOptionView()

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