package cn.tmc.msg

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import cn.bmob.v3.Bmob
import cn.tmc.msg.adapter.EMMessageAdapter
import cn.tmc.msg.ui.activity.ChatActivity
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMOptions
import com.hyphenate.chat.EMTextMessageBody

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 09
 * version: 1.0
 * description
 */
class MsgApplication: Application() {
    companion object {
        lateinit var appContext:MsgApplication
    }
    val soundPool= SoundPool(2,AudioManager.STREAM_MUSIC,0)
    val duan by lazy { soundPool.load(appContext,R.raw.duan,0) }
    val chang by lazy { soundPool.load(appContext,R.raw.yulu,0) }
    val messageListener=object :EMMessageAdapter(){
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            if(isRunInForeGround()){
                soundPool.play(duan,1f,1f,0,0,1f)
            }else{
                soundPool.play(chang,1f,1f,0,0,1f)
                showNotification(p0)
            }
        }
    }

    private fun showNotification(p0: MutableList<EMMessage>?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId="MsgApp"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, "MsgNotice", NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        var text=getString(R.string.no_text_message)
        p0?.forEach {
            if(it.type==EMMessage.Type.TXT){
                text=(it.body as EMTextMessageBody).message
            }
            val intent= Intent(appContext,ChatActivity::class.java)
            intent.putExtra("username",it.conversationId())
            val stackBuilder = TaskStackBuilder.create(appContext).addParentStack(ChatActivity::class.java).addNextIntent(intent)
            val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            val notification=NotificationCompat.Builder(appContext,channelId)
                    .setContentTitle(getString(R.string.receive_new_message))
                    .setContentText(text)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_funchat)
                    .build()
            //反射让app图标显示消息数量
            val field = notification.javaClass.getDeclaredField("extraNotification")
            val extraNotification = field.get(notification)
            val method = extraNotification.javaClass.getDeclaredMethod("setMessageCount", java.lang.Integer.TYPE)
            method.invoke(extraNotification,EMClient.getInstance().chatManager().unreadMessageCount)
            notificationManager.notify(0,notification)
        }
    }

    private fun isRunInForeGround(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses
        for(process in runningAppProcesses){
            if(process.processName==packageName){
                return process.importance== ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            }
        }
        return false
    }

    override fun onCreate() {
        super.onCreate()
        appContext=this
        //初始化 easemob
        EMClient.getInstance().init(applicationContext, EMOptions())
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG)
        //初始化 bmob
        Bmob.initialize(this, "d93e072d823089427b87a5b0e4591521")
        EMClient.getInstance().chatManager().addMessageListener(messageListener)
    }


}