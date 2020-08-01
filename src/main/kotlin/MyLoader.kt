package net.mamoe.mirai.simpleloader

import kotlinx.coroutines.*
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.join
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.nameCardOrNick
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.event.events.MemberMuteEvent
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.content
import java.io.File

    suspend fun main() {
        println("输入QQ号：")
        var qqId: Int = Integer.valueOf(readLine())
        println("输入密码：")
        val password: String? = readLine()
        println("开启功能的QQ群号: ")
        var qqGroupNb: Int = Integer.valueOf(readLine())
        //val qqId = 12345678L//Bot的QQ号，需为Long类型，在结尾处添加大写L
        //val password = "WyIlY0528"//Bot的密码
        //val qqGroupNb = 1020604475L //开启功能的QQ群号
        val miraiBot = Bot(qqId.toLong(), password.toString()).alsoLogin()//新建Bot并登录
        val myTestGroup=miraiBot.getGroup(qqGroupNb.toLong())
        miraiBot.subscribeMessages {
            /*"你好" reply "你好!"
            case("我爱你") {
                reply(At(sender as Member) + " 我也是! ")
            }
            (contains("在") or contains("有空么")) {
                reply("不在, 没空")
            }*/
        }
        miraiBot.subscribeAlways<GroupMessageEvent> { event ->
            // 暂时实现方法是对某一个人的消息进行跟随, 后期会使用定时器实现
            if (event.group==myTestGroup) {
                if (event.message.content.contains("雨今日健康良好且未有行程变动")) {
                    val report_image = "./test.png" //如有截图请替换路径
                    reply("李跃明今日健康良好且未有行程变动! 并亲了你一口:)")
                    File(report_image).sendAsImage()
                }
            }
        }
        /*
        miraiBot.subscribeAlways<MemberJoinEvent> {
            it.group.sendMessage(PlainText("欢迎 ${it.member.nameCardOrNick} 加入本群！"))
        }
        miraiBot.subscribeAlways<MemberMuteEvent> {
            it.group.sendMessage(PlainText("恭喜 ${it.member.nameCardOrNick} 喜提禁言套餐一份"))
        }*/
        miraiBot.join() // 等待 Bot 离线, 避免主线程退出
    }
