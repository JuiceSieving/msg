package cn.tmc.msg.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import cn.tmc.msg.R
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sp

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 12
 * version: 1.0
 * description
 */
class Slidebar:View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private var sectionHeight= 0f
    private var textBaseLine=0f
    companion object {
        val SECTION= arrayOf("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")
    }

    init {
        paint.textSize = sp(12).toFloat()
        paint.color = resources.getColor(R.color.qq_section_text_color)
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var y= textBaseLine
        val x= width/2f
        SECTION.forEach {
            canvas?.drawText(it,x,y,paint)
            y+= sectionHeight
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        sectionHeight = h.toFloat() / SECTION.size
        val textHeight = paint.fontMetrics.descent-paint.fontMetrics.ascent
        textBaseLine = sectionHeight/2 + textHeight/2 - paint.fontMetrics.descent
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                setBackgroundResource(R.drawable.bg_slide_bar)
                val position = getLetterPosition(event)
                val letter = SECTION[position]
                onSectionChangeListener?.let {
                    it.onSelectSection(letter)
                }
            }
            MotionEvent.ACTION_MOVE->{
                val position = getLetterPosition(event)
                val letter = SECTION[position]
                onSectionChangeListener?.let {
                    it.onSelectSection(letter)
                }
            }
            MotionEvent.ACTION_UP->{
                backgroundColor=Color.TRANSPARENT
                onSectionChangeListener?.let {
                    it.onSlideDone()
                }
            }
        }
        return true
    }

    private fun getLetterPosition(event: MotionEvent): Int {
        var index = (event.y / sectionHeight).toInt()
        if(index<0){
            index=0
        }else if (index> SECTION.size-1){
            index= SECTION.size-1
        }
        return index
    }

    var onSectionChangeListener: OnSectionChangeListener? =null
    interface OnSectionChangeListener{
        fun onSelectSection(letter:String)
        fun onSlideDone()
    }
}